package com.example.matchashop.models;

public class ProductModel {

    private int id;
    private String productName;
    private String productPhotoTitle;
    private double productPrice;
    private String productDescription;

    // creating constructor for our variables.
    public ProductModel(int id, String productName, String productPhotoTitle, double productPrice, String productDescription) {
        super();
        this.id = id;
        this.productName = productName;
        this.productPhotoTitle = productPhotoTitle;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }

    public ProductModel(String productName, String productPhotoTitle, double productPrice, String productDescription) {
        super();
        this.productName = productName;
        this.productPhotoTitle = productPhotoTitle;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }

    public ProductModel(String productName) {
        this.productName = productName;
        this.productPhotoTitle = "";
        this.productPrice = 200;
        this.productDescription = "";
    }

    public int getId() {return id;}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPhotoTitle() {
        return productPhotoTitle;
    }

    public void setProductPhotoTitle(String productPhotoTitle) {
        this.productPhotoTitle = productPhotoTitle;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


}
