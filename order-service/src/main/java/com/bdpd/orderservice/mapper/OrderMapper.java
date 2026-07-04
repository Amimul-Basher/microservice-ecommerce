package com.bdpd.orderservice.mapper;

import com.bdpd.orderservice.dto.*;
import com.bdpd.orderservice.model.Order;
import com.bdpd.orderservice.model.OrderLineItem;

public class OrderMapper {
    public static Order toOrder(OrderRequest orderRequest){
        return Order.builder()
                .orderLineItemList(
                        orderRequest.orderLineItemRequestList()
                                .stream()
                                .map(OrderMapper::toOrderLineItem)
                                .toList()
                )
                .build();
    }

    private static OrderLineItem toOrderLineItem(OrderLineItemRequest orderLineItemRequest) {
        return OrderLineItem.builder()
                .price(orderLineItemRequest.price())
                .skuCode(orderLineItemRequest.skuCode())
                .quantity(orderLineItemRequest.quantity())
                .build();
    }

    public static OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderNumber(order.getOrderNumber())
                .orderPlacedBy(order.getOrderPlacedBy())
                .orderLineItemResponseList(
                        order.getOrderLineItemList().stream()
                                .map(OrderMapper::toOrderLineItemResponse)
                                .toList()
                )
                .build();
    }

    public static OrderItemCheckRequest toOrderItemCheckRequest(OrderLineItemRequest orderLineItemRequest){
        return OrderItemCheckRequest.builder()
                .skuCode(orderLineItemRequest.skuCode())
                .quantity(orderLineItemRequest.quantity())
                .build();
    }

    private static OrderLineItemResponse toOrderLineItemResponse(OrderLineItem orderLineItem) {
        return OrderLineItemResponse.builder()
                .skuCode(orderLineItem.getSkuCode())
                .price(orderLineItem.getPrice())
                .quantity(orderLineItem.getQuantity())
                .build();
    }

}
