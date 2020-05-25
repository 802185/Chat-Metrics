package com.example.metrics.component;

import com.example.metrics.model.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.metrics.service.TrendingCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.List;

@Component
public class RabbitMQMessageReceiver {

    @Value("${metrics.rabbitmq.exchange}")
    private String exchange;

    @Value("${metrics.rabbitmq.topics.trending}")
    private String trendingKey;

    @Autowired
    private TrendingCalculatorService trendingCalculatorService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues="${metrics.rabbitmq.queue}", containerFactory = "listenerFactory")
    public void receiveMessage(List<Message> m) throws Exception {
      trendingCalculatorService.receiveMessage(m);
      TrendingResponse results  = trendingCalculatorService.calculateResults();
      System.out.println(results);
      rabbitTemplate.convertAndSend(exchange, trendingKey, results);
    }
}