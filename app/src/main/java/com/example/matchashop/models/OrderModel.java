package com.example.matchashop.models;

import com.google.type.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderModel{
    private List<productQuantity> products;
    private Date orderDate;
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
}
