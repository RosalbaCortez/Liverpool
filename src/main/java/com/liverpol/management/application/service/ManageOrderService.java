package com.liverpol.management.application.service;

import com.liverpol.management.application.usecase.GetOrderItemUseCase;
import com.liverpol.management.domain.model.DisplayNameMatcher;
import com.liverpol.management.domain.model.StoreName;
import com.liverpol.management.domain.model.dto.*;
import com.liverpol.management.infrastructure.adapter.mapper.ItemMapper;
import com.liverpol.management.infrastructure.adapter.out.rest.ItemMockApiAdapter;
import com.liverpol.management.infrastructure.adapter.out.rest.OrderMockApiAdapter;
import com.liverpol.management.infrastructure.dto.ItemMockApi;
import com.liverpol.management.infrastructure.dto.OrderMockApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManageOrderService implements GetOrderItemUseCase {
    private final ItemMockApiAdapter itemMockApiAdapter;
    private final OrderMockApiAdapter orderMockApiAdapter;


    @Override
    public List<Order> getOrderItems() {
        List<OrderMockApi> ordersMockApi = orderMockApiAdapter.getAllOrdersMockApi();
        List<ItemMockApi> itemsMockApi = itemMockApiAdapter.getAllMockApiItems();
        Map<String, String> itemsQuantity = itemsMockApi.stream().collect(Collectors.toMap(ItemMockApi::itemId, ItemMockApi::quantity));
        List<Order> orders = ordersMockApi.stream()
                .filter(mock -> mock.items() != null)
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

    public List<OrderMockApi> getOrderMockApi() {
        return orderMockApiAdapter.getAllOrdersMockApi();
    }

    @Override
    public List<OrderDTO> getOrderByRef(String orderRef) {
        if (orderRef == null || orderRef.isBlank()) {
            return List.of();
        }
        List<OrderMockApi> allOrders = orderMockApiAdapter.getAllOrdersMockApi();
        List<ItemMockApi> allItems = itemMockApiAdapter.getAllMockApiItems();
        Map<String, String> itemsDisplayName = allItems.stream().collect(Collectors.toMap(ItemMockApi::itemId, ItemMockApi::displayName, (existing, replace) -> existing));
        return allOrders.stream().filter(order -> orderRef.equals(order.orderRef()))
                .map(order -> {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setCanal(order.canal());
                    orderDTO.setUserId(order.userId());
                    orderDTO.setOrderStatus(String.valueOf(order.orderStatus()));
                    orderDTO.setOrderRef(orderRef);
                    List<ItemDTO> itemsDTO = order.items().stream().map(item -> {
                        ItemDTO itemDTO = new ItemDTO();
                        itemDTO.setItem(item);
                        itemDTO.setDisplayName(itemsDisplayName.getOrDefault(item, "NOT found"));
                        return itemDTO;
                    }).toList();
                    orderDTO.setItems(itemsDTO);
                    return orderDTO;
                }).toList();

    }

    @Override
    public List<OrderDTO> getOrderByStatus(String orderStatus) {
        List<OrderMockApi> allOrders = new ArrayList<>();
        if (orderStatus == null || orderStatus.isBlank()) {
            allOrders = orderMockApiAdapter.getAllOrdersMockApi();
        } else {
            allOrders = orderMockApiAdapter.getAllOrdersMockApiByOrderStatus(orderStatus.trim());
        }
        if (allOrders == null || allOrders.isEmpty()) {
            return List.of();
        }
        List<ItemMockApi> allItems = itemMockApiAdapter.getAllMockApiItems();
        Map<String, String> itemsDisplayName = (allItems == null ? List.<ItemMockApi>of() : allItems).stream()
                .filter(item -> item != null && item.itemId() != null)
                .collect(Collectors.toMap(
                        ItemMockApi::itemId,
                        item -> item.displayName() != null ? item.displayName() : "NOT found",
                        (existing, replace) -> existing
                ));
        return allOrders.stream()
                .filter(Objects::nonNull)
                .map(order -> {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setOrderStatus(String.valueOf(order.orderStatus()));
                    orderDTO.setOrderRef(order.orderRef());
                    orderDTO.setUserId(order.userId());
                    orderDTO.setCanal(order.canal());
                    List<ItemDTO> items = (order.items() == null ? List.<String>of() : order.items()).stream()
                            .map(itemId -> {
                                ItemDTO itemDTO = new ItemDTO();
                                itemDTO.setItem(itemId);
                                itemDTO.setDisplayName(
                                        itemId != null ? itemsDisplayName.getOrDefault(itemId, "NOT found") : "NOT found"
                                );
                                return itemDTO;
                            })
                            .toList();
                    orderDTO.setItems(items); // ajusta el nombre del setter según tu OrderDTO
                    return orderDTO;
                })
                .toList();
    }

    @Override
    public List<OrderStoreDTO> getOrderByStoreName(String storeName) {
        List<OrderMockApi> allOrders = orderMockApiAdapter.getAllOrdersMockApi();
        List<ItemMockApi> allItems = itemMockApiAdapter.getAllMockApiItems();
        Map<String, String> itemsDisplayName = allItems.stream().collect(Collectors.toMap(ItemMockApi::itemId, ItemMockApi::displayName, (existing, replace) -> existing));
        String normalizedFilter = StoreName.normalize(storeName);
        return allOrders.stream().filter(order -> StoreName.normalize(order.storeName()).contains(normalizedFilter))
                .map(order -> {
                    OrderStoreDTO orderDTO = new OrderStoreDTO();
                    orderDTO.setCanal(order.canal());
                    orderDTO.setUserId(order.userId());
                    orderDTO.setOrderStatus(String.valueOf(order.orderStatus()));
                    orderDTO.setOrderRef(order.orderRef());
                    orderDTO.setStoreName(order.storeName());
                    List<ItemDTO> itemsDTO = order.items().stream().map(item -> {
                        ItemDTO itemDTO = new ItemDTO();
                        itemDTO.setItem(item);
                        itemDTO.setDisplayName(itemsDisplayName.getOrDefault(item, "NOT found"));
                        return itemDTO;
                    }).toList();
                    orderDTO.setItems(itemsDTO);
                    return orderDTO;
                }).toList();

    }

    public List<Item> getItemsByDisplayName(String displayName) {
        List<ItemMockApi> allItems = itemMockApiAdapter.getAllMockApiItems();
        return allItems.stream()
                .filter(item -> DisplayNameMatcher.normalize(item.displayName()).contains(DisplayNameMatcher.normalize(displayName))).map(ItemMapper::toDomain).toList();
    }
}
