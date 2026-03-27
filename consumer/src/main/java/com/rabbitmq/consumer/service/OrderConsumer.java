package com.rabbitmq.consumer.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.consumer.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "order.queue")
    public void receiveOrder(OrderDTO order, Message message, Channel channel) throws IOException, InterruptedException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        Thread.sleep(3000);

        try {
            log.info("[CONSUMER] Mensagem recebida - deliveryTag={} payload={}", deliveryTag, order);

            if (order.getDescription() != null) {
                String description = order.getDescription().toLowerCase();

                if (description.contains("error")){
                    log.error("[CONSUMER] Simulando falha: NACK sem requeue");
                    channel.basicNack(deliveryTag, false, false);
                    return;
                }

                if (description.contains("requeue")){
                    log.warn("[CONSUMER] Simulando falha: NACK com requeue=true");
                    channel.basicNack(deliveryTag, false, true);
                    return;
                }

                if (description.contains("noack")){
                    log.warn("[CONSUMER] Simulando comportamento sem ACK: não envia ack/nack");
                    // Não faz nada: a mensagem ficará não-confirmada até o consumidor desconectar
                    return;
                }
            }

            // Simula processamento
            log.info("[CONSUMER] Processando pedido: {} - {} unidades de {}",
                    order.getOrderId(), order.getQuantity(), order.getDescription());

            // Confirmar manualmente após sucesso
            channel.basicAck(deliveryTag, false);

            log.info("[CONSUMER] ACK enviado - Mensagem confirmada e removida da fila");

        } catch (IOException e) {
            log.error("[CONSUMER] Erro ao processar mensagem: {}", e.getMessage());
            // Como fallback, descarta a mensagem
            channel.basicNack(deliveryTag, false, false);
            log.warn("[CONSUMER] NACK enviado - Mensagem descartada (requeue=false)");
        }

    }

    @RabbitListener(queues = "log.queue", containerFactory = "autoAckRabbitListenerContainerFactory")
    public void receveLog(Message message) throws InterruptedException {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        Thread.sleep(3000);
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            log.info("[LOG_CONSUMER] Erro não tratado" );
        }
        log.info("[LOG_CONSUMER] Mensagem de log recebida: {}", body);

    }
}
