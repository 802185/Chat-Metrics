package com.example.metrics.component;

import com.example.metrics.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.metrics.service.TrendingCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Component
public class RabbitMQMessageReceiver {

    @Autowired
    private TrendingCalculatorService trendingCalculatorService;

    @Autowired
    private RabbitTemplate op;

    @RabbitListener(queues="${metrics.rabbitmq.queue}", containerFactory = "listenerFactory")
    public void receiveMessage(List<Message> m) {
        // System.out.println(m.getBody());
        // System.out.println(m.getSent_from());
        // System.out.println(m.getSent_to());

      System.out.println(m.size());
      trendingCalculatorService.receiveMessage(m);

      List<Map<String, Double>> results = trendingCalculatorService.calculateZScores();

      System.out.println(results);

      op.convertAndSend("/topic/metrics.trending", results);

    }
}