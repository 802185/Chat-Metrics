package com.example.metrics.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Random;

public class Group implements Serializable {

    private long id;

    private String name;

    private User owner;

    private String url;

    private List<Message> messages;

    private List<User> members;

    public Group(String name, User owner) {
        this.name = name;
        this.owner = owner;

        Random rand = new Random();
        rand.setSeed(Instant.now().getEpochSecond());
        this.url = "/groups/" + name + Math.abs(rand.nextInt());
    }

    public Group() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

}