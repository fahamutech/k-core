package com.fahamutech.kcore.admin.model;

public interface ICategory {
    String getImage();
    String getId();
    String getDescription();
    String getName();
    void setImage(String image);
    void setId(String id);
    void setDescription(String description);
    void setName(String name);
}
