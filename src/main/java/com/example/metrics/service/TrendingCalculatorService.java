package com.example.metrics.service;


import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.lang.Math;


import com.example.metrics.model.*;
import org.springframework.stereotype.Service;



@Service
public class TrendingCalculatorService {

//    public static final String RECEIVE_METHOD_NAME = "receiveMessage";

    private List<Message> messagePopulation;

    private Map<String, Integer> wordCount;

    private Map<String, Integer> sentToCount;

    private Map<String, Integer> sentFromCount;

    private TrendingResponse trendingResponse;


    public TrendingCalculatorService() {
        messagePopulation = new ArrayList<Message>();
        wordCount = new HashMap<String, Integer>();
        sentToCount = new HashMap<String, Integer>();
        sentFromCount = new HashMap<String, Integer>();
        trendingResponse = new TrendingResponse();
    }


    public List<Map<String, Double>> calculateZScores(){

        List<Map<String, Double>> resultSet = new ArrayList<Map<String, Double>>();

        Map<String, Double> wordScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, wordCount);

            wordScores.put(currentWord, currentWordScore);

        }


        resultSet.add(wordScores);

        Map<String, Double> sentScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sentToCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, sentToCount);


            sentScores.put(currentWord, currentWordScore);

        }

        resultSet.add(sentScores);

        Map<String, Double> fromScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sentFromCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, sentFromCount);

            fromScores.put(currentWord, currentWordScore);

        }

        resultSet.add(fromScores);


        return resultSet;
    }


    public TrendingResponse calculateResults() {

        Map<String, Double> wordScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, wordCount);

            wordScores.put(currentWord, currentWordScore);

        }

        trendingResponse.setWordScores(wordScores);
        

        
        Map<String, Double> senderScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sentToCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, sentToCount);


            senderScores.put(currentWord, currentWordScore);

        }

        trendingResponse.setSenderScores(senderScores);
        


        Map<String, Double> receiverScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sentFromCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, sentFromCount);

            receiverScores.put(currentWord, currentWordScore);

        }

        trendingResponse.setReceiverScores(receiverScores);

        
        trendingResponse.setWordCount(wordCount);
        trendingResponse.setSentToCount(sentToCount);
        trendingResponse.setSentFromCount(sentFromCount);

        return trendingResponse;
        
    }


    public double calculateZScore(String word, Map<String, Integer> population) {

        int populationSize = population.size();

        int sumPopulation = 0;
        for (Map.Entry<String, Integer> entry : population.entrySet()) {

            String currentWord = entry.getKey();
            Integer currentWordCount = entry.getValue();

            sumPopulation = sumPopulation + currentWordCount;

        }

        double averagePopulation = (double) sumPopulation / populationSize;
        List<Double> stdList = new ArrayList<Double>();
        double stdSumPopulation = (double) 0;


        for (Map.Entry<String, Integer> entry : population.entrySet()) {

            String currentWord = entry.getKey();
            Integer currentWordCount = entry.getValue();

            double std = Math.pow(currentWordCount - averagePopulation, 2);


            stdList.add(std);
            stdSumPopulation = stdSumPopulation + std;
        }

        double stdPopulation = Math.sqrt(stdSumPopulation / populationSize);


        if (stdPopulation == 0) {
            return (double) 0;
        } 

        return ((population.get(word) - averagePopulation) / stdPopulation);
    }

    private Map<String, Integer> countWords(String text, Map<String, Integer> map, boolean split) {

        if (split) {
            for(String word : text.split("\\W")) {

                if (word.isEmpty()) { continue; }

                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
        } else {

            if (map.containsKey(text)) {
                map.put(text, map.get(text) + 1);
            } else {
                map.put(text, 1);
            }
        }


        return map;
    }


    public void receiveMessage(List<Message> messageList) {


        for (Message m : messageList) {
            String body = m.getBody().toLowerCase();
            String from = m.getSent_from().toLowerCase();
            String to = m.getSent_to().toLowerCase();

            wordCount = countWords(body, wordCount, true);
            sentToCount = countWords(from, sentToCount, false);
            sentFromCount = countWords(to, sentFromCount, false);

            messagePopulation.add(m);
        }

        // System.out.println("########################");
        // System.out.println("wordCount:");
        // wordCount.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " " + entry.getValue());  
        // });
        
        // System.out.println("########################");
        // System.out.println("sentToCount:");
        // sentToCount.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " " + entry.getValue());  
        // });
        
        // System.out.println("########################");
        // System.out.println("sentFromCount:");
        // sentFromCount.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " " + entry.getValue());  
        // });

    }

    public void receiveMessage(Message m) {

        // System.out.println(m.getBody());
        // System.out.println(m.getSent_from());
        // System.out.println(m.getSent_to());

        String body = m.getBody().toLowerCase();
        String from = m.getSent_from().toLowerCase();
        String to = m.getSent_to().toLowerCase();

        wordCount = countWords(body, wordCount, true);
        sentToCount = countWords(from, sentToCount, false);
        sentFromCount = countWords(to, sentFromCount, false);

        messagePopulation.add(m);


        // System.out.println("########################");
        // System.out.println("wordCount:");
        // wordCount.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " " + entry.getValue());  
        // });
        
        // System.out.println("########################");
        // System.out.println("sentToCount:");
        // sentToCount.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " " + entry.getValue());  
        // });
        
        // System.out.println("########################");
        // System.out.println("sentFromCount:");
        // sentFromCount.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " " + entry.getValue());  
        // });

    }
}