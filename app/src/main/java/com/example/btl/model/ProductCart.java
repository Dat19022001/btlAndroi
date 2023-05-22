package com.example.btl.model;

import java.io.Serializable;

public class ProductCart implements Serializable {
    private String productId,productName,productImg;
    private int productPrice,quantity;

    public ProductCart(String productId, String productName, String productImg, int productPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
