package com.example.matchashop.models;

import java.util.ArrayList;

public class DiscountModel {
    public String DiscountName;
    public String DiscountCode;
    public String DiscountDescription;
    public String DiscountValue;
    public String DiscountStartDate;
    public String DiscountEndDate;
    public int Reoccuring;
    public ArrayList<ProductModel> DiscountProducts;

    //Setters
    public void setDiscountName(String DiscountName) {
        this.DiscountName = DiscountName;
    }

    public void setDiscountCode(String DiscountCode) {
        this.DiscountCode = DiscountCode;
    }

    public void setDiscountDescription(String DiscountDescription) {
        this.DiscountDescription = DiscountDescription;
    }

    public void setDiscountValue(String DiscountValue) {
        this.DiscountValue = DiscountValue;
    }

    public void setDiscountEndDate(String DiscountEndDate) {
        this.DiscountEndDate = DiscountEndDate;
    }

    public void setDiscountProducts(ArrayList<ProductModel> DiscountProducts) {
        this.DiscountProducts = DiscountProducts;
    }
}
