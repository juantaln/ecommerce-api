package com.portafolio.ecommerce_api.controllers;

import com.portafolio.ecommerce_api.dto.OrderDto;
import com.portafolio.ecommerce_api.entities.Order;
import com.portafolio.ecommerce_api.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") 
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto, Authentication authentication) { // 2.
        String username = authentication.getName(); 
        Order createdOrder = orderService.createOrder(orderDto, username);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
}