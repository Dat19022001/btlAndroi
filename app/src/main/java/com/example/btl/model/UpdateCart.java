package com.example.btl.model;

import java.io.Serializable;

public class UpdateCart implements Serializable {

    private String UserId;
    private String type;
    private String productId;

    public UpdateCart(String userId, String type, String productId) {
        UserId = userId;
        this.type = type;
        this.productId = productId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
