package com.example.foodrecipes.model;

import java.io.Serializable;
import java.util.List;

public class SendList implements Serializable {
    private static final long serialVersionUID = -42699087862739696L;
    String SendName;
    List<OneFavorityMessage> FavMeg;
    public   SendList(String SendName,List<OneFavorityMessage> FavMeg){
        this.FavMeg = FavMeg;
        this.SendName = SendName;
    }
    public SendList(){

    }

    public String getSendName(){
        return SendName;
    }
    public void setSendName(String SendName){
        this.SendName = SendName;
    }
    public List<OneFavorityMessage> getFavMeg(){
        return FavMeg;

    }
    public void setFavMeg (List<OneFavorityMessage> FavMeg){
        this.FavMeg = FavMeg;
    }
}

