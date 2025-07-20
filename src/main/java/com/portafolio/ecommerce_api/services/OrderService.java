package com.portafolio.ecommerce_api.services;

import com.portafolio.ecommerce_api.dto.OrderDto;
import com.portafolio.ecommerce_api.entities.*;
import com.portafolio.ecommerce_api.repositories.OrderRepository;
import com.portafolio.ecommerce_api.repositories.ProductRepository;
import com.portafolio.ecommerce_api.repositories.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
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
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private EmailService emailService; // <-- 1. INYECTAR EL SERVICIO DE EMAIL

    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(OrderDto orderDto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (var itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: ID " + itemDto.getProductId()));

            if (product.getStock() < itemDto.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getName());
            }

            totalPrice = totalPrice.add(product.getPrice().multiply(new BigDecimal(itemDto.getQuantity())));
        }

        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(totalPrice);
            System.out.println("Intención de pago creada exitosamente: " + paymentIntent.getId());
        } catch (StripeException e) {
            throw new RuntimeException("Error al procesar el pago con Stripe: " + e.getMessage(), e);
        }

        for (var itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);

            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        
        emailService.sendOrderConfirmationEmail(savedOrder);

        return savedOrder;
    }
}