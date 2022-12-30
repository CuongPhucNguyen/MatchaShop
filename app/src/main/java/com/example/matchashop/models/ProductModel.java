package com.example.matchashop.models;

public class ProductModel {

    private String productName;
    private String productPhotoTitle;
    private String productPrice;
    private String productDescription;

    // creating constructor for our variables.
    public ProductModel(String productName, String productPhotoTitle, String productPrice, String productDescription) {
        this.productName = productName;
        this.productPhotoTitle = productPhotoTitle;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }
    public ProductModel(String productName) {
        this.productName = productName;
        this.productPhotoTitle = "";
        this.productPrice = "$200";
        this.productDescription = "";
    }

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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


}
