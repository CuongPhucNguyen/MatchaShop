package com.example.matchashop.models;

import java.util.List;

public class UserModel {
    private String Username;
    private String Email;
    private String userImgURL;
    private List<OrderModel> orders;

    public UserModel(String username, String email, String userImgURL, List<OrderModel> orders) {
        Username = username;
        Email = email;
        this.userImgURL = userImgURL;
        this.orders = orders;
    }

    public UserModel() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserImgURL() {
        return userImgURL;
    }

    public void setUserImgURL(String userImgURL) {
        this.userImgURL = userImgURL;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }
}
