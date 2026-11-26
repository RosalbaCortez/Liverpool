package com.liverpol.management.domain.model.port.output;

import com.liverpol.management.domain.model.dto.Order;

import java.util.List;

public interface OrderExternalPort {
    public List<Order> getOrdersMockApi();
}
