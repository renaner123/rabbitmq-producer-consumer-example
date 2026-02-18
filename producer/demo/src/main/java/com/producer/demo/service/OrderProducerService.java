package com.producer.demo.service;

import com.producer.demo.config.RabbitMQConfig;
import com.producer.demo.controller.OrderController;
import com.producer.demo.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(OrderProducerService.class);

    public OrderProducerService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishOrder(OrderDTO order){
        try {
            log.info("Publicando pedido: {}", order);

            byte[] payload = objectMapper.writeValueAsBytes(order);

            MessageProperties props = new MessageProperties();
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

            Message message = new Message(payload, props);

            rabbitTemplate.send(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_ORDER_CREATED, message);

            log.info("Mensagem enviada com sucesso!");

        } catch (Exception e) {
            log.error("Falha ao enviar a mensagem: {}", e.getMessage());
        }

    }

}
