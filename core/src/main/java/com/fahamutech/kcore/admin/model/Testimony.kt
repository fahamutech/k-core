package com.fahamutech.kcore.admin.model;

import java.io.Serializable;

public class Testimony implements Serializable , ITestimony {
    private String id;
    private String image;
    private String date;

    public Testimony(){

    }

    public Testimony(String image, String date) {
        this.image = image;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
