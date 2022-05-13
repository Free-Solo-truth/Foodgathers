package com.example.foodrecipes.model;

import java.io.Serializable;

/**
 * (Userid)实体类
 *
 * @author makejava
 * @since 2022-05-09 00:28:18
 */
public class UpdateUser implements Serializable {
    private static final long serialVersionUID = -29734583582174557L;

    private Integer id;

    private String username;

    private String email;

    private String phone;

    private String password;

    private String photo;

    public UpdateUser(String name,String email,String phone,String password,String photo){
        this.username=name;
        this.email=email;
        this.phone=phone;
        this.password=password;
        this.photo = photo;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}

