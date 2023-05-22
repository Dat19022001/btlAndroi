package com.example.btl.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetBill implements Serializable {
    private int Status;

    private String message;

    @SerializedName("data1")
    private List<Bill> list;


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

    public List<Bill> getList() {
        return list;
    }

    public void setList(List<Bill> list) {
        this.list = list;
    }
}
