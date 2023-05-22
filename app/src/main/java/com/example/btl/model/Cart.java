package com.example.btl.model;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {

    private String UserId;
    private List<ProductCart> products;

    public Cart(String userId, List<ProductCart> products) {
        UserId = userId;
        this.products = products;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public List<ProductCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductCart> products) {
        this.products = products;
    }
}
