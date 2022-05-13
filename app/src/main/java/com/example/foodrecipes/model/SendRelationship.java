package com.example.foodrecipes.model;

import java.io.Serializable;
import java.util.List;

public class SendRelationship implements Serializable {
    private static final long serialVersionUID = 720683124706580190L;


    private String name;
    private List<Relationship> ListChatPerson;
    public  SendRelationship(String sendname,List<Relationship> ListChatPerson){
        this.ListChatPerson = ListChatPerson;
        this.name = sendname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setListChatPerson(List<Relationship> listChatPerson) {
        ListChatPerson = listChatPerson;
    }

    public List<Relationship> getListChatPerson() {
        return ListChatPerson;
    }
}

