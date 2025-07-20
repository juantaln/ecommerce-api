package com.portafolio.ecommerce_api.services;

import com.portafolio.ecommerce_api.entities.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // 1. Añadimos un logger profesional
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendOrderConfirmationEmail(Order order) {
       
        log.info("Iniciando envío de email asíncrono para la orden #{}", order.getId());

        try {

            Thread.sleep(5000);

            SimpleMailMessage message = new SimpleMailMessage();
            
            message.setFrom("juandavidtl1998@gmail.com");
            message.setTo(order.getUser().getEmail());
            message.setSubject("Confirmación de tu pedido #" + order.getId());
            message.setText("¡Gracias por tu compra, " + order.getUser().getUsername() + "!\n\n" +
                            "Hemos recibido tu pedido con un total de $" + order.getTotalPrice() + ".\n\n" +
                            "Saludos,\nEl equipo de E-commerce API.");

            mailSender.send(message);

            log.info("Email de confirmación enviado exitosamente a {}", order.getUser().getEmail());

        } catch (Exception e) {
         
            log.error("Error al enviar el email para la orden #{}:", order.getId(), e);
        }
    }
}