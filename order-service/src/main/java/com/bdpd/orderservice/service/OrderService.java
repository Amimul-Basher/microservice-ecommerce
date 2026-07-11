package com.bdpd.orderservice.service;

import com.bdpd.orderservice.dto.OrderRequest;
import com.bdpd.orderservice.dto.OrderResponse;
import com.bdpd.orderservice.model.Order;

import java.util.List;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);

    List<OrderResponse> findAllOrders();
}
