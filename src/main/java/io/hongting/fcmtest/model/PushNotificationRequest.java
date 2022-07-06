package io.hongting.fcmtest.model;

/**
 * @author hongting
 * @create 2022 06 29 10:22 AM
 */

public class PushNotificationRequest {
    
    private String title;

    private String message;

    private String topic;

    private String token;


    public PushNotificationRequest() {
    }

    public PushNotificationRequest(String title, String message, String topic, String token) {
        this.title = title;
        this.message = message;
        this.token = token;
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic(){ return topic; }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
