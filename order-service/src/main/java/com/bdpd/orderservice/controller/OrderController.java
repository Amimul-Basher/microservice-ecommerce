package com.bdpd.orderservice.controller;

import com.bdpd.orderservice.dto.OrderRequest;
import com.bdpd.orderservice.dto.OrderResponse;
import com.bdpd.orderservice.model.Order;
import com.bdpd.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Created successfully";
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrder(){
        return ResponseEntity.ok(orderService.findAllOrders());
    }
}
