package com.portafolio.ecommerce_api.services;

import com.portafolio.ecommerce_api.dto.OrderDto;
import com.portafolio.ecommerce_api.entities.*;
import com.portafolio.ecommerce_api.repositories.OrderRepository;
import com.portafolio.ecommerce_api.repositories.ProductRepository;
import com.portafolio.ecommerce_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional 
    public Order createOrder(OrderDto orderDto, String username) {
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (var itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

          
            if (product.getStock() < itemDto.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);

         
            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);

        
            totalPrice = totalPrice.add(product.getPrice().multiply(new BigDecimal(itemDto.getQuantity())));
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }
}
