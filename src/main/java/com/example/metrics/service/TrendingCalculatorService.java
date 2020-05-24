package com.example.metrics.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.lang.Math;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.example.metrics.model.*;
import org.springframework.stereotype.Service;

@Service
public class TrendingCalculatorService {

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


    public Map<String, Double> sortMapByValue(Map<String, Double> map) {
        Map<String, Double> sortedMap = new HashMap<>();
        map.entrySet().stream()
                        .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                        .forEach(k -> sortedMap.put(k.getKey() , k.getValue()));

        return sortedMap;
    }

    public TrendingResponse calculateResults() {

        Map<String, Double> wordScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, wordCount);

            wordScores.put(currentWord, currentWordScore);

        }


        Map<String, Double> sortedWordScores = wordScores.entrySet().stream()
                                                .sorted(Entry.<String, Double>comparingByValue().reversed())
                                                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                                                          (e1, e2) -> e1, LinkedHashMap::new));
        trendingResponse.setWordScores(sortedWordScores);

        
        Map<String, Double> senderScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sentToCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, sentToCount);


            senderScores.put(currentWord, currentWordScore);

        }

        Map<String, Double> sortedSenderScores = senderScores.entrySet().stream()
                                                .sorted(Entry.<String, Double>comparingByValue().reversed())
                                                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                                                          (e1, e2) -> e1, LinkedHashMap::new));
        trendingResponse.setSenderScores(sortedSenderScores);
        

        Map<String, Double> receiverScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sentFromCount.entrySet()) {

            String currentWord = entry.getKey();
            double currentWordScore = calculateZScore(currentWord, sentFromCount);

            receiverScores.put(currentWord, currentWordScore);

        }

        Map<String, Double> sortedReceiverScores = receiverScores.entrySet().stream()
                                                .sorted(Entry.<String, Double>comparingByValue().reversed())
                                                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                                                          (e1, e2) -> e1, LinkedHashMap::new));

        trendingResponse.setReceiverScores(sortedReceiverScores);


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
    }

    public void receiveMessage(Message m) {
        String body = m.getBody().toLowerCase();
        String from = m.getSent_from().toLowerCase();
        String to = m.getSent_to().toLowerCase();

        wordCount = countWords(body, wordCount, true);
        sentToCount = countWords(from, sentToCount, false);
        sentFromCount = countWords(to, sentFromCount, false);

        messagePopulation.add(m);
    }
}