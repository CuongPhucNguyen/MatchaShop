package com.example.matchashop.models;

import java.util.ArrayList;

public class DiscountModel {
    private String DiscountName;
    private String DiscountCode;
    private int DiscountPercentage;

    public DiscountModel(String discountName, String discountCode, int discountPercentage) {
        DiscountName = discountName;
        DiscountCode = discountCode;
        DiscountPercentage = discountPercentage;
    }

    public DiscountModel() {
    }

    public String getDiscountName() {
        return DiscountName;
    }

    public void setDiscountName(String discountName) {
        DiscountName = discountName;
    }

    public String getDiscountCode() {
        return DiscountCode;
    }

    public void setDiscountCode(String discountCode) {
        DiscountCode = discountCode;
    }

    public int getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        DiscountPercentage = discountPercentage;
    }
}
