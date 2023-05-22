package com.example.btl.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private String UserId,content,date;

    public Comment(String userId, String content, String date) {
        UserId = userId;
        this.content = content;
        this.date = date;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
