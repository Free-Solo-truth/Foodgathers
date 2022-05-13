package com.example.foodrecipes.model;

import java.io.Serializable;
import java.lang.reflect.Constructor;

public class DynamicMessage implements Serializable {
    private static final long serialVersionUID = 350430364635859205L;

    private String userIamge;

    private String userName;

    private String dynamicText;

    private String dynamicImage;



    public String getUserIamge() {
        return userIamge;
    }

    public void setUserIamge(String userIamge) {
        this.userIamge = userIamge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDynamicText() {
        return dynamicText;
    }

    public void setDynamicText(String dynamicText) {
        this.dynamicText = dynamicText;
    }

    public String getDynamicImage() {
        return dynamicImage;
    }

    public void setDynamicImage(String dynamicImage) {
        this.dynamicImage = dynamicImage;
    }

}
