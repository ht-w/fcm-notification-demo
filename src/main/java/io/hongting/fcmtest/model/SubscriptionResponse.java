package io.hongting.fcmtest.model;

/**
 * @author hongting
 * @create 2022 06 30 12:31 PM
 */
public class SubscriptionResponse {

    private int status;

    private String message;

    public SubscriptionResponse() {
    }

    public SubscriptionResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
