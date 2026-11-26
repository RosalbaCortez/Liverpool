package com.liverpol.management.application.service;


import com.liverpol.management.application.usecase.CreateClientUseCase;
import com.liverpol.management.application.usecase.GetClient;
import com.liverpol.management.application.usecase.GetOrdersByUser;
import com.liverpol.management.domain.model.dto.Client;
import com.liverpol.management.domain.model.dto.Order;
import com.liverpol.management.infrastructure.adapter.out.rest.ItemMockApiAdapter;
import com.liverpol.management.infrastructure.adapter.out.rest.MongoDBAdapter;
import com.liverpol.management.infrastructure.adapter.out.rest.OrderMockApiAdapter;
import com.liverpol.management.infrastructure.dto.ItemMockApi;
import com.liverpol.management.infrastructure.dto.OrderMockApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService implements GetOrdersByUser, CreateClientUseCase, GetClient {
    private final ItemMockApiAdapter itemMockApiAdapter;
    private final OrderMockApiAdapter orderMockApiAdapter;
    private final MongoDBAdapter mongoDBAdapter;


    @Override
    public List<Order> getOrdersByUser(String userID) {

        CompletableFuture<List<OrderMockApi>> ordersFuture = CompletableFuture.supplyAsync(() -> orderMockApiAdapter.getAllOrdersMockApi()).orTimeout(4, TimeUnit.SECONDS);
        CompletableFuture<List<ItemMockApi>> itemsFuture = CompletableFuture.supplyAsync(() -> itemMockApiAdapter.getAllMockApiItems()).orTimeout(4, TimeUnit.SECONDS);
        List<OrderMockApi> ordersMockApi = ordersFuture.join();
        List<ItemMockApi> itemsMockApi = itemsFuture.join();

        Map<String, String> itemsQuantity = itemsMockApi.stream().collect(Collectors.toMap(ItemMockApi::itemId, ItemMockApi::quantity));

        List<Order> orders = ordersMockApi.stream()
                .filter(mock -> mock.userId().equals(userID))
                .flatMap(mock -> mock.items().stream()
                        .filter(itemsQuantity::containsKey) // Opcional: omite items que no existan en el mapa
                        .map(itemId -> new Order(
                                itemId,
                                Integer.parseInt(itemsQuantity.get(itemId)), // Convierte String a int
                                mock.canal(),
                                mock.orderStatus(), mock.orderRef()
                        ))
                )
                .toList();


        return orders;
    }

    public Client updateOrdersClient(String userID) {
        Client client = mongoDBAdapter.findByID(userID).orElseThrow(() -> new RuntimeException("Client NOT foud -->"));
        List<Order> orders = getOrdersByUser(userID);
        client.setOrders(orders);
        return mongoDBAdapter.save(client);
    }

    @Override
    public List<Order> getOrdersAllUsers() {
        return List.of();
    }

    @Override
    public Client saveClient(Client client) {
        return mongoDBAdapter.save(client);
    }

    @Override
    public Client findByID(String userId) {
        return mongoDBAdapter.findByID(userId).orElseThrow(() -> new RuntimeException("Client not Found"));
    }


}
