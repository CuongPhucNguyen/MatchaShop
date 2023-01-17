package com.example.matchashop.models;

import java.util.List;

public class CartModel {
    private List<productQuantity> products;

    public CartModel(List<productQuantity> products) {
        this.products = products;
    }

    public CartModel() {
    }

    public List<productQuantity> getProducts() {
        return products;
    }

    public void setProducts(List<productQuantity> products) {
        this.products = products;
    }
}
