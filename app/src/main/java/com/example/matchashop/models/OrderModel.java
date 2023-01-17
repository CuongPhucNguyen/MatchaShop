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



class productQuantity {
    private String productId;
    private int quantity;

    public productQuantity(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public productQuantity() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
