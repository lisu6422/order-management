package com.example.ordermanagement.infrastructure.mq;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCancelRabbitSender {

    private final RabbitTemplate rabbitTemplate;

    final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("消息ACK结果:" + ack + ", correlationData: " + correlationData.getId());
        }
    };

    public void send(Object message, Map<String, Object> properties) {

        MessageHeaders mhs = new MessageHeaders(properties);
        Message<?> msg = MessageBuilder.createMessage(message, mhs);

        rabbitTemplate.setConfirmCallback(confirmCallback);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        MessagePostProcessor mpp = message1 -> {
            System.err.println("---> post to do: " + message1);
            return message1;
        };

        rabbitTemplate.convertAndSend("ORDER_CANCEL", "springboot.rabbit", msg, mpp, correlationData);

    }
}
