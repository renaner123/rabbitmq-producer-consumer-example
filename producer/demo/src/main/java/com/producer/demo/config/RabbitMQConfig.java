package com.producer.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "order.exchange";
    public static final String QUEUE_NAME = "order.queue";
    public static final String QUEUE_USER = "user.queue";
    public static final String QUEUE_LOG = "log.queue";
    public static final String ROUTING_KEY_ORDER_CREATED = "order.created";
    public static final String ROUTING_KEY_ORDER_UPDATED = "order.updated";
    public static final String ROUTING_KEY_USER_UPDATED = "user.updated";
    public static final String ROUTING_KEY_USER_CREATED = "user.created.ok";
    public static final String ROUTING_KEY_USER_DELETED = "payment.created";

    public static final String BINDING_KEY_ORDER = "order.*";
    public static final String BINDING_KEY_USER = "user.*";
    public static final String BINDING_KEY_LOG = "#";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

//    @Bean
//    public DirectExchange exchange() {
//        return new DirectExchange(EXCHANGE_NAME, true, false);
//    }
//
//    @Bean
//    public FanoutExchange exchange() {
//        return new FanoutExchange(EXCHANGE_NAME, true, false);
//    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Queue queueUser() {
        return new Queue(QUEUE_USER, true);
    }

    @Bean
    public Queue queueLog() {
        return new Queue(QUEUE_LOG, true);
    }

    @Bean
    public Binding bindingOrder(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(BINDING_KEY_ORDER);
    }

    @Bean
    public Binding bindingUser(Queue queueUser, TopicExchange exchange) {
        return BindingBuilder.bind(queueUser).to(exchange).with(BINDING_KEY_USER);
    }

    @Bean
    public Binding bindingLog(Queue queueLog, TopicExchange exchange) {
        return BindingBuilder.bind(queueLog).to(exchange).with(BINDING_KEY_LOG);
    }


    @Bean
    public SimpleMessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public ApplicationRunner rabbitInit(RabbitAdmin admin){
        return args -> admin.initialize();
    }

}
