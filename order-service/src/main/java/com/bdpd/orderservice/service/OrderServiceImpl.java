package com.bdpd.orderservice.service;

import com.bdpd.orderservice.dto.OrderRequest;
import com.bdpd.orderservice.dto.OrderResponse;
import com.bdpd.orderservice.mapper.OrderMapper;
import com.bdpd.orderservice.model.Order;

import com.bdpd.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;


    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = OrderMapper.toOrder(orderRequest);
        order.setOrderNumber(UUID.randomUUID().toString());
        //Even though we are saving the order and have the list items added to the order, We need order to be added to the orderItem as well
        // Otherwise while creating orderitems it won't be able to connect the orderItems to the order. OrderItems foreign key "order_id" will be null.
        //because when we created the orderItem didn't set it's order field with corresponding order.
        for(var listItem : order.getOrderLineItemList())
            listItem.setOrder(order);
        orderRepository.save(order);
        log.info("Order Placed successfully");
    }

    @Override
    public List<OrderResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> OrderMapper.toOrderResponse(order))
                .toList();

    }
}
