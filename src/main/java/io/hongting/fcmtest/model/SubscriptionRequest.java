package io.hongting.fcmtest.model;

import java.util.List;

/**
 * @author hongting
 * @create 2022 06 30 10:27 AM
 */


public class SubscriptionRequest {

    private String topic;

    private List<String> tokens;

    public SubscriptionRequest() {
    }

    public SubscriptionRequest(String topic, List<String> tokens) {
        this.topic = topic;
        this.tokens = tokens;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}