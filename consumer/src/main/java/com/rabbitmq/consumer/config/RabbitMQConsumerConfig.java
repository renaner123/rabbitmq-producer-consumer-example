package com.rabbitmq.consumer.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.amqp.autoconfigure.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {

    /**
     * O Configurer ajuda a aplicar as propriedades do application.yml/properties
     * automaticamente à nossa factory manual.
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            MessageConverter messageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        // Aplica as configurações padrão do Spring (retry, concurrency, etc.)
        configurer.configure(factory, connectionFactory);

        // Garante que não trava se a fila ainda não existir no broker
        factory.setMissingQueuesFatal(false);
        factory.setMessageConverter(messageConverter);

        return factory;
    }

    /**
     * Fábrica específica para quando queres que o RabbitMQ considere a mensagem
     * como lida assim que ela chega ao consumidor (Auto Acknowledge).
     */
    @Bean
    public SimpleRabbitListenerContainerFactory autoAckRabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            MessageConverter messageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);

        factory.setMessageConverter(messageConverter);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setMissingQueuesFatal(false);

        return factory;
    }

    /**
     * Conversor para transformar o corpo da mensagem (JSON) em objetos Java.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}