package com.example.btl.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookAll implements Serializable {

    private int Status;

    private String message;

    @SerializedName("data1")
    private List<Book> list;

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

    public List<Book> getList() {
        return list;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }
}
