package com.example.foodrecipes.model;

import java.io.Serializable;

/**
 * (Relationship)实体类
 *
 * @author makejava
 * @since 2022-05-06 16:01:23
 */
public class Relationship implements Serializable {
    private static final long serialVersionUID = 720683124706580190L;


    private String useremail;
    private String username;
    private String userphoto;
    private String friendname;
    private String friendphone;
    private String friendemail;

    public Relationship(String friendemail, String friendphone, String friendname){
        this.friendemail = friendemail;
        this.friendphone = friendphone;
        this.friendname = friendname;
    }
    public Relationship(String useremail, String username, String userphoto, String friendemail, String friendphone, String friendname){
        this.useremail = useremail;
        this.username = username;
        this.userphoto = userphoto;
        this.friendemail = friendemail;
        this.friendphone = friendphone;
        this.friendname = friendname;
    }

    public Relationship(){

    }



    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setFriendemail(String friendemail) {
        this.friendemail = friendemail;
    }

    public String getFriendemail() {
        return friendemail;
    }

    public void setFriendphone(String friendphone) {
        this.friendphone = friendphone;
    }

    public String getFriendphone() {
        return friendphone;
    }



    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }



}

