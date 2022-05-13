package com.example.foodrecipes.model;

import java.io.Serializable;

public class Carrier_FavorityMessage implements Serializable {
    private static final long serialVersionUID = -42699087862739696L;
    String SendName;
    OneFavorityMessage FavMeg;
    public Carrier_FavorityMessage(String SendName, OneFavorityMessage FavMeg){
        this.FavMeg = FavMeg;
        this.SendName = SendName;
    }
    public String getSendName(){
        return SendName;
    }
    public void setSendName(String SendName){
        this.SendName = SendName;
    }
    public OneFavorityMessage getFavMeg(){
        return FavMeg;

    }
    public void setFavMeg (OneFavorityMessage FavMeg){
        this.FavMeg = FavMeg;
    }
}
