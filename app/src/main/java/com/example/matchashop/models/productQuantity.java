package com.example.matchashop.models;

public class productQuantity {
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
