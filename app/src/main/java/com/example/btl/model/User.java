package com.example.btl.model;

import java.io.Serializable;

public class User implements Serializable {
    private String email,username,password,profileImg,sex,phone,address;

    private boolean admin;

    public User() {
    }

    public User(String email, String username, String password, String profileImg, String sex, String phone, String address) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.profileImg = profileImg;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
    }

    public User(String email, String username, String profileImg, String phone, String address) {
        this.email = email;
        this.username = username;
        this.profileImg = profileImg;
        this.phone = phone;
        this.address = address;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String email, String username, String password, boolean admin) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
