package com.example.foodrecipes.model;

import java.io.File;
import java.io.Serializable;

public class UserData implements Serializable {
    /*问题  Serializable中的SerialVersionUID的定义*/
    private static final long serialVersionUID= -3109789286645296932L;
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String photo;

    public UserData(String name,String email,String phone,String password){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.password=password;
    }

    public UserData(String name,String email,String phone,String password,String photo){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.password=password;
        this.photo = photo;
    }
    public UserData(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto(){return photo; }

    public void setPhoto(String photo){this.photo = photo;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

