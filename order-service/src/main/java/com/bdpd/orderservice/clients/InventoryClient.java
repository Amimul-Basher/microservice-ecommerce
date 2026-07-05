package com.bdpd.orderservice.clients;


import com.bdpd.orderservice.dto.OrderItemCheckRequest;
import com.bdpd.orderservice.dto.OrderItemsCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryClient {
    private final WebClient webClient;
    public OrderItemsCheckResponse checkInventoryWithOrderItemRequests(List<OrderItemCheckRequest> request){

        return webClient.post()
                .uri("/match")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OrderItemsCheckResponse.class)
                .block() //for Synchronization
                ;
    }
}
