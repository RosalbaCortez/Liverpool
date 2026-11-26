package com.liverpol.management.application.service;

import com.liverpol.management.application.usecase.GetOrderUseCase;
import com.liverpol.management.domain.model.dto.Order;
import com.liverpol.management.infrastructure.adapter.out.rest.OrderMockApiAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements GetOrderUseCase {

    private final OrderMockApiAdapter orderAdapter;

    @Override
    public List<Order> getOrders() {
        return orderAdapter.getOrdersMockApi();
    }


}
