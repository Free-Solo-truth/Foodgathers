package com.example.foodrecipes.model;

import java.io.Serializable;
import java.util.List;

public class SendUserData implements Serializable {
    /*问题  Serializable中的SerialVersionUID的定义*/
    private static final long serialVersionUID= -3109789286645296932L;
    private List<UserData> ListUserData;

    public void setListUserData(List<UserData> listUserData) {
        ListUserData = listUserData;
    }

    public List<UserData> getListUserData() {
        return ListUserData;
    }
}
