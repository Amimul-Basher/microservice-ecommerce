package com.bdpd.orderservice.clients;


import com.bdpd.orderservice.dto.OrderItemCheckRequest;
import com.bdpd.orderservice.dto.OrderItemsCheckResponse;
import com.bdpd.orderservice.handler.exception.InventoryServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {
    private final WebClient webClient;

    @CircuitBreaker(name = "inventory", fallbackMethod = "checkInventoryFallback")
    @TimeLimiter(name = "inventory") //This will make the request wait for certain time to get the response otherwise throw time limit exception
    @Retry(name = "inventory")
    public CompletableFuture<OrderItemsCheckResponse> checkInventoryWithOrderItemRequests(List<OrderItemCheckRequest> request){
              return webClient.post()
                    .uri("/match")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OrderItemsCheckResponse.class)
//                    .block() //for Synchronization
                    .toFuture()
                    ;
    }

    public CompletableFuture<OrderItemsCheckResponse> checkInventoryFallback(List<OrderItemCheckRequest> request, Exception ex){
//        return CompletableFuture.supplyAsync(() -> {
//            throw new InventoryServiceUnavailableException("Inventory service is down, Please order after some time", ex);
//        });
        log.error("Fallback invoked due to: {}", ex.getClass().getName(), ex);
        return CompletableFuture.failedFuture(
                new InventoryServiceUnavailableException(
                        "Inventory service is down, Please order after some time", ex));
    }
}
