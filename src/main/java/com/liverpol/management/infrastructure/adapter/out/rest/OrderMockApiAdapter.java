package com.liverpol.management.infrastructure.adapter.out.rest;

import com.liverpol.management.domain.model.dto.Order;
import com.liverpol.management.domain.model.port.output.OrderExternalPort;
import com.liverpol.management.infrastructure.dto.OrderMockApi;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OrderMockApiAdapter implements OrderExternalPort {
    private final RestClient restClient;

    public OrderMockApiAdapter() {
        this.restClient = RestClient.builder()
                .baseUrl("https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1")
                .build();
    }


    @Override
    public List<Order> getOrdersMockApi() {
        List<OrderMockApi> ordersMockApi = restClient.get().uri("/pedidos").retrieve().body(new ParameterizedTypeReference<List<OrderMockApi>>() {
        });
        List<Order> orders = ordersMockApi.stream().map(order -> new Order("1", 2, order.canal(), order.orderStatus(), "2333")).toList();
        return orders;
    }

    @Cacheable("ordersAllMock")
    public List<OrderMockApi> getAllOrdersMockApi() {
        List<OrderMockApi> ordersMockApi = restClient.get().uri("/pedidos").retrieve().body(new ParameterizedTypeReference<List<OrderMockApi>>() {
        });
        return ordersMockApi;
    }

    public List<OrderMockApi> getAllOrdersMockApiByOrderStatus(String orderStatus) {
        String cleanFilter = (orderStatus == null) ? "" : orderStatus.trim();
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/pedidos")
                        .queryParam("orderStatus", orderStatus)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<OrderMockApi>>() {
                });

    }


}
