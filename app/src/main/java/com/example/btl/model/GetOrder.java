package com.example.btl.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOrder implements Serializable {
    @SerializedName("Status")
    private int status;
    private String message;
    private String link;
    @SerializedName("data1")
    private Order order;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
