package com.example.matchashop.models;

import com.google.type.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderModel{
    private List<productQuantity> products;
    private Date orderDate;
    private double totalPrice;

    public OrderModel(List<productQuantity> products, double totalPrice) {
        this.products = products;
        this.orderDate = Calendar.getInstance().getTime();
        this.totalPrice = totalPrice;
    }

    public OrderModel() {
    }

    public List<productQuantity> getProducts() {
        return products;
    }

    public void setProducts(List<productQuantity> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}



