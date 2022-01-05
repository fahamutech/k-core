package com.fahamutech.kcore.admin.model;

import java.io.Serializable;

public class Category implements Serializable, ICategory {
    private String id;
    private String name;
    private String description;
    private String image;

    public Category() {

    }

    public Category(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
