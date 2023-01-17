package com.example.matchashop.models;

import java.util.ArrayList;

public class ShopModel {
    public ArrayList<ProductModel> products;
    public ArrayList<DiscountModel> discounts;
    public String shopName;
    public String shopDescription;
    public String shopPhotoTitle;
    public String shopAddress;
    public String shopPhoneNumber;
    public String shopEmail;

    //Setters
    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public void setShopPhotoTitle(String shopPhotoTitle) {
        this.shopPhotoTitle = shopPhotoTitle;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    public void setDiscounts(ArrayList<DiscountModel> discounts) {
        this.discounts = discounts;
    }



}
