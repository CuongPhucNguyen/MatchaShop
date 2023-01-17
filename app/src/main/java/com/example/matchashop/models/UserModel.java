package com.example.matchashop.models;

import java.util.List;

public class UserModel {
    private String Username;
    private String Email;
    private String userImgURL;
    private List<OrderModel> orders;
    private CartModel cart;


    public UserModel() {
    }

    public UserModel(String username, String email, String userImgURL, List<OrderModel> orders, CartModel cart) {
        Username = username;
        Email = email;
        this.userImgURL = userImgURL;
        this.orders = orders;
        this.cart = cart;
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

    public CartModel getCart() {
        return cart;
    }

    public void setCart(CartModel cart) {
        this.cart = cart;
    }
}
