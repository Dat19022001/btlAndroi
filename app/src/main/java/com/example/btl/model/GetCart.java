package com.example.btl.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetCart implements Serializable {
    private int Status;

    private String message;

    @SerializedName("data1")
    private Cart cart;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}

