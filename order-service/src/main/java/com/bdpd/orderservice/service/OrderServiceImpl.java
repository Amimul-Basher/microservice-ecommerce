package com.bdpd.orderservice.service;

import com.bdpd.orderservice.clients.InventoryClient;
import com.bdpd.orderservice.dto.OrderItemsCheckResponse;
import com.bdpd.orderservice.dto.OrderRequest;
import com.bdpd.orderservice.dto.OrderResponse;
import com.bdpd.orderservice.event.OrderPlacedEvent;
import com.bdpd.orderservice.mapper.OrderMapper;
import com.bdpd.orderservice.model.Order;

import com.bdpd.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final WebClient webClient;

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    //Always specify the key value type
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    //Just adding a dummy email as security is not implemented
    private String email = "amimul@gmail.com";

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = OrderMapper.toOrder(orderRequest);
        order.setOrderNumber(UUID.randomUUID().toString());
        //Even though we are saving the order and have the list items added to the order, We need order to be added to the orderItem as well
        // Otherwise while creating orderitems it won't be able to connect the orderItems to the order. OrderItems foreign key "order_id" will be null.
        //because when we created the orderItem didn't set its order field with corresponding order.
        for(var listItem : order.getOrderLineItemList())
            listItem.setOrder(order);

        //Before saving the order I have to check the order items are available in the inventory
        //Send a query request to inventory item with orderItemCheckRequest(skuCode, quantity)

        //Step 1: take all the order items and map it to OrderItemCheckRequest
        var orderItemCheckRequestList = orderRequest.orderLineItemRequestList().stream()
                .map(OrderMapper::toOrderItemCheckRequest)
                        .toList();

        //step 2: Send the query using WebClient and receive the inventoryItemResponseList
        CompletableFuture<OrderItemsCheckResponse> future = inventoryClient.checkInventoryWithOrderItemRequests(orderItemCheckRequestList);


        //Step 3: Check Inventory item request and order check request match

        OrderItemsCheckResponse orderItemsCheckResponse = null;
        try {
            orderItemsCheckResponse = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        if(orderItemsCheckResponse.getAllInStock() == 1){
            orderRepository.save(order);
            log.info("Order Placed successfully");

            //Send message to kafka topic
            //Order number and Email
            OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                    .orderNumber(order.getOrderNumber())
                    .email(this.email)
                    .build();
            log.info("Start sending orderplacedevent {} to kafka topic", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End - Sending the order placed event to kafka");

            return "Order Successful";
        }else{
            log.warn("Check Order Items");
            return "Check Order Items";
        }

//        return String.valueOf(inventoryClient.checkInventoryWithOrderItemRequests(orderItemCheckRequestList)
//                .thenApply(response -> {
//                            if (response.getAllInStock() == 1) {
//                                orderRepository.save(order);
//                                log.info("Order is placed successfully");
//                                return "Order placed successfully";
//                            }else{
//                                log.info("order couldn't be placed");
//                                return "Please review the order";
//                            }
//
//                        })
//                .exceptionally(ex -> {
//                    ex.printStackTrace();
//                    return null;
//                }));
        //Step 4: if check request match save else throw an exception

//        orderRepository.save(order);

    }

    @Override
    public List<OrderResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> OrderMapper.toOrderResponse(order))
                .toList();

    }
}
