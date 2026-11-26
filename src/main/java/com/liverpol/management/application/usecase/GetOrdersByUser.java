package com.liverpol.management.application.usecase;

import com.liverpol.management.domain.model.dto.Order;

import java.util.List;

public interface GetOrdersByUser {
    public List<Order> getOrdersByUser(String userID);

    public List<Order> getOrdersAllUsers();

}
