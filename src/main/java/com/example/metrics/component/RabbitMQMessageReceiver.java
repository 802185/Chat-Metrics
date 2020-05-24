package com.example.metrics.component;

import com.example.metrics.model.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.metrics.service.TrendingCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;


@Component
public class RabbitMQMessageReceiver {

    @Value("${metrics.rabbitmq.topics.trending}")
    private String trendingTopic;

    @Autowired
    private TrendingCalculatorService trendingCalculatorService;

    @Autowired
    private RabbitTemplate op;

    @RabbitListener(queues="${metrics.rabbitmq.queue}", containerFactory = "listenerFactory")
    public void receiveMessage(List<Message> m) throws Exception {
        // System.out.println(m.getBody());
        // System.out.println(m.getSent_from());
        // System.out.println(m.getSent_to());

      System.out.println(m.size());
      trendingCalculatorService.receiveMessage(m);

      // List<Map<String, Double>> results = trendingCalculatorService.calculateZScores();
      TrendingResponse results  = trendingCalculatorService.calculateResults(); 

      ObjectMapper objectMapper = new ObjectMapper();
      String jsonResponse;
      try {
        jsonResponse = objectMapper.writeValueAsString(results);
        System.out.println(jsonResponse);
      }  catch (Exception e) {
        throw new Exception("Can't convert to json: ", e);
      }

      op.convertAndSend("/topic/"+trendingTopic, jsonResponse);

    }
}