package io.hongting.fcmtest.model;

/**
 * @author hongting
 * @create 2022 06 29 10:24 AM
 */
public class PushNotificationResponse {

    private int status;

    private String message;

    public PushNotificationResponse() {
    }

    public PushNotificationResponse(int status, String message) {
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
