package com.example.metrics.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.lang.Math;

public class TrendingResponse implements Serializable {

    private Map<String, Double> wordScores;

    private Map<String, Double> senderScores;

    private Map<String, Double> receiverScores;

    private Map<String, Integer> wordCount;

    private Map<String, Integer> sentToCount;

    private Map<String, Integer> sentFromCount;

    public TrendingResponse(Map<String, Double> wordScores,
                            Map<String, Double> senderScores,
                            Map<String, Double> receiverScores,
                            Map<String, Integer> wordCount,
                            Map<String, Integer> sentToCount,
                            Map<String, Integer> sentFromCount) {

        this.wordScores = wordScores;
        this.senderScores = senderScores;
        this.receiverScores = receiverScores;
        this.wordCount = wordCount;
        this.sentToCount = sentToCount;
        this.sentFromCount = sentFromCount;

    }

    public TrendingResponse() {
        this.wordScores = new HashMap<String, Double>();
        this.senderScores = new HashMap<String, Double>();
        this.receiverScores = new HashMap<String, Double>();
        this.wordCount = new HashMap<String, Integer>();
        this.sentToCount = new HashMap<String, Integer>();
        this.sentFromCount = new HashMap<String, Integer>();
    }

    public Map<String, Double> getWordScores() {
        return wordScores;
    }

    public void setWordScores(Map<String, Double> wordScores) {
        this.wordScores = wordScores;
    }

    public Map<String, Double> getSenderScores() {
        return senderScores;
    }

    public void setSenderScores(Map<String, Double> senderScores) {
        this.senderScores = senderScores;
    }

    public Map<String, Double> getReceiverScores() {
        return receiverScores;
    }

    public void setReceiverScores(Map<String, Double> receiverScores) {
        this.receiverScores = receiverScores;
    }

    public Map<String, Integer> getWordCount() {
        return wordCount;
    }

    public void setWordCount(Map<String, Integer> wordCount) {
        this.wordCount = wordCount;
    }

    public Map<String, Integer> getSentToCount() {
        return sentToCount;
    }

    public void setSentToCount(Map<String, Integer> sentToCount) {
        this.sentToCount = sentToCount;
    }

    public Map<String, Integer> getSentFromCount() {
        return sentFromCount;
    }

    public void setSentFromCount(Map<String, Integer> sentFromCount) {
        this.sentFromCount = sentFromCount;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "TrendingResponse{" +
                "wordScores=" + wordScores +
                ", senderScores=" + senderScores +
                ", receiverScores=" + receiverScores +
                ", wordCount=" + wordCount +
                ", sentToCount=" + sentToCount +
                ", sentFromCount=" + sentFromCount +
                '}';
    }
}