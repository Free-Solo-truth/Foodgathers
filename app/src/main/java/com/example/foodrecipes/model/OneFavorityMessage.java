package com.example.foodrecipes.model;

import java.io.Serializable;


public class OneFavorityMessage implements Serializable {
    private static final long serialVersionUID = -42699087862739696L;



    private String favorityimage;

    private String favoritytitle;

    private String favoritysubit;

    private Integer favoritynumber;

    private Integer favoritytime;

    private boolean favoritytype;

    public OneFavorityMessage() {

    }

    public OneFavorityMessage(String favorityimage,String favoritytitle,String favoritysubit,Integer favoritynumber,Integer favoritytime, boolean favoritytype){
        this.favorityimage = favorityimage;
        this.favoritytitle = favoritytitle;
        this.favoritysubit = favoritysubit;
        this.favoritynumber = favoritynumber;
        this.favoritytime = favoritytime;
        this.favoritytype = favoritytype;
    }


    public String getFavorityimage() {
        return favorityimage;
    }

    public void setFavorityimage(String favorityimage) {
        this.favorityimage = favorityimage;
    }

    public String getFavoritytitle() {
        return favoritytitle;
    }

    public void setFavoritytitle(String favoritytitle) {
        this.favoritytitle = favoritytitle;
    }

    public String getFavoritysubit() {
        return favoritysubit;
    }

    public void setFavoritysubit(String favoritysubit) {
        this.favoritysubit = favoritysubit;
    }

    public Integer getFavoritynumber() {
        return favoritynumber;
    }

    public void setFavoritynumber(Integer favoritynumber) {
        this.favoritynumber = favoritynumber;
    }

    public Integer getFavoritytime() {
        return favoritytime;
    }

    public void setFavoritytime(Integer favoritytime) {
        this.favoritytime = favoritytime;
    }

    public boolean getFavoritytype() {
        return favoritytype;
    }

    public void setFavoritytype(boolean favoritytype) {
        this.favoritytype = favoritytype;
    }

}

