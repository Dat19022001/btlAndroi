package com.example.btl.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookDetail implements Serializable {
    private int Status;

    private String message;

    @SerializedName("data1")
    private Book book;

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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
