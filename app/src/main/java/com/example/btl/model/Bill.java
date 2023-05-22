package com.example.btl.model;

import java.io.Serializable;
import java.util.List;

public class Bill implements Serializable {

    private String userId,phone,address,date;

    private float tong;

    private List<ProductCart> products;

    public Bill(String userId, String phone, String address, String date, float tong, List<ProductCart> products) {
        this.userId = userId;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.tong = tong;
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTong() {
        return tong;
    }

    public void setTong(float tong) {
        this.tong = tong;
    }

    public List<ProductCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductCart> products) {
        this.products = products;
    }
}
