package io.hongting.fcmtest.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hongting
 * @create 2022 07 01 9:11 AM
 */

public class FcmRequest {

    private String collapse_key;
    private FcmNotification notification;
    private FcmData data;
    private String to;


    public String getCollapse_key() {
        return collapse_key;
    }

    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }

    public FcmNotification getNotification() {
        return notification;
    }
    public void setNotification(FcmNotification notification) {
        this.notification = notification;
    }
    public FcmData getData() {
        return data;
    }
    public void setData(FcmData data) {
        this.data = data;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
}