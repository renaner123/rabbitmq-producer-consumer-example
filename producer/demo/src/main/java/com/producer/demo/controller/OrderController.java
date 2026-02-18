package com.producer.demo.controller;

import com.producer.demo.dto.OrderDTO;
import com.producer.demo.service.OrderProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderProducerService orderProducerService;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderProducerService orderProducerService){
        this.orderProducerService = orderProducerService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO order){
        log.info("Recebida requisição para criar pedido: {}", order);
        orderProducerService.publishOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido criado e publicado no RabbitMQ");

    }



}
