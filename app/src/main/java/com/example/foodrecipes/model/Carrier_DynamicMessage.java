package com.example.foodrecipes.model;

import java.io.Serializable;
import java.util.List;

public class Carrier_DynamicMessage implements Serializable {
    private static final long serialVersionUID = 350430364635859205L;
    String SendName;
    List<DynamicMessage> DynMsg;
    public   Carrier_DynamicMessage(String SendName,List<DynamicMessage> FavMeg){
        this.DynMsg = FavMeg;
        this.SendName = SendName;
    }
    public String getSendName(){
        return SendName;
    }
    public void setSendName(String SendName){
        this.SendName = SendName;
    }
    public List<DynamicMessage> getDynMsg(){
        return DynMsg;

    }
    public void setDynMsg(List<DynamicMessage> FavMeg){
        this.DynMsg = FavMeg;
    }
}