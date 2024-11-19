package com.example.order_service.controller;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@Transactional
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
       return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));

    }
    @GetMapping
    public String welcome(){
        return "Please welcome on our ";
    }

    public String fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return "OOPs! sorry Try again";
    }
}
