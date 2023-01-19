package com.example.matchashop.MainFragments.coupon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CouponViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CouponViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}