package com.liverpol.management.application.usecase;

import com.liverpol.management.domain.model.dto.Order;
import com.liverpol.management.domain.model.dto.OrderDTO;
import com.liverpol.management.domain.model.dto.OrderStoreDTO;

import java.util.List;

public interface GetOrderItemUseCase {

    public List<Order> getOrderItems();

    public List<OrderDTO> getOrderByRef(String orderRef);

    public List<OrderDTO> getOrderByStatus(String orderStatus);

    public List<OrderStoreDTO> getOrderByStoreName(String orderStatus);

}
