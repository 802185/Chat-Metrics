package com.example.metrics.component;

import com.example.metrics.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessageReceiver {

    @RabbitListener(queues="${metrics.rabbitmq.queue}", containerFactory = "listenerFactory")
    public void receiveMessage(Message m) {
        System.out.println(m.getBody());
        System.out.println(m.getSent_from());
        System.out.println(m.getSent_to());
    }
}