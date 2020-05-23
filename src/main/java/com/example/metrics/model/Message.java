package com.example.metrics.model;

import java.io.Serializable;

public class Message implements Serializable {

  private long id;

  private String body;

  private String sent_from;

  private String sent_to;

  private String timestamp;

  private Group group;

  public Message() {
    super();
  }

  public Message(String body, String sent_from, String sent_to, String timestamp) {
    this.body = body;
    this.sent_from = sent_from;
    this.sent_to = sent_to;
    this.timestamp = timestamp;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getSent_from() {
    return sent_from;
  }

  public void setSent_from(String sent_from) {
    this.sent_from = sent_from;
  }

  public String getSent_to() {
    return sent_to;
  }

  public void setSent_to(String sent_to) {
    this.sent_to = sent_to;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

}