package io.hongting.fcmtest.model;

/**
 * @author hongting
 * @create 2022 07 01 9:02 AM
 */


public class FcmData {

    private String name;

    private String gender;

    public FcmData() {
    }

    public FcmData(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
