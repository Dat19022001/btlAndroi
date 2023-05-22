package com.example.btl.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private String userId,phone,date,address;

    private float tong;

    @SerializedName("products")
    private List<ProductCart> list;

    public Order(String userId, String phone, String date, String address, float tong, List<ProductCart> list) {
        this.userId = userId;
        this.phone = phone;
        this.date = date;
        this.address = address;
        this.tong = tong;
        this.list = list;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTong() {
        return tong;
    }

    public void setTong(float tong) {
        this.tong = tong;
    }

    public List<ProductCart> getList() {
        return list;
    }

    public void setList(List<ProductCart> list) {
        this.list = list;
    }
}
