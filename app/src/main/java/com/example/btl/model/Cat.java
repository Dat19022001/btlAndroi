package com.example.btl.model;

import java.io.Serializable;

public class Cat implements Serializable {
    private String imgUrl,name;

    public Cat() {
    }

    public Cat(String imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
