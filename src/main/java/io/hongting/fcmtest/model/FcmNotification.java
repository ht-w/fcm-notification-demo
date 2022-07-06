package io.hongting.fcmtest.model;

/**
 * @author hongting
 * @create 2022 07 01 9:09 AM
 */


public class FcmNotification {

    private String title;

    private String body;

    public FcmNotification() {
    }

    public FcmNotification(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
