package com.example.btl.model;

import com.google.gson.annotations.SerializedName;

public class AddCart {
    private String UserId;
    @SerializedName("products")
    private ProductCart productCart;

    public AddCart(String userId, ProductCart productCart) {
        UserId = userId;
        this.productCart = productCart;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public ProductCart getProductCart() {
        return productCart;
    }

    public void setProductCart(ProductCart productCart) {
        this.productCart = productCart;
    }
}
