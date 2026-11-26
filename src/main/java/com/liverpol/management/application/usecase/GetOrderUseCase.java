package com.liverpol.management.application.usecase;

import com.liverpol.management.domain.model.dto.Order;

import java.util.List;


public interface GetOrderUseCase {

    public List<Order> getOrders();


}
