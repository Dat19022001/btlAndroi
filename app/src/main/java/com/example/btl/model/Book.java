package com.example.btl.model;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {

    private String id,name,author,imgUrl,datePublish,description;
    private Cat cat;

    private int pageNumber,buyNumber;

    private float price,rate;

    private List<Comment> commentList;

    public Book(String id, String name, String author, String imgUrl, String datePublish, String description, Cat cat, int pageNumber, int buyNumber, float price, float rate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.imgUrl = imgUrl;
        this.datePublish = datePublish;
        this.description = description;
        this.cat = cat;
        this.pageNumber = pageNumber;
        this.buyNumber = buyNumber;
        this.price = price;
        this.rate = rate;
    }

    public Book(String id, String name, String author, String imgUrl, String datePublish, String description, Cat cat, int pageNumber, int buyNumber, float price, float rate, List<Comment> commentList) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.imgUrl = imgUrl;
        this.datePublish = datePublish;
        this.description = description;
        this.cat = cat;
        this.pageNumber = pageNumber;
        this.buyNumber = buyNumber;
        this.price = price;
        this.rate = rate;
        this.commentList = commentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(String datePublish) {
        this.datePublish = datePublish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
