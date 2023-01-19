package com.example.matchashop.MainFragments.dashboard.CouponForm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matchashop.R;
import com.example.matchashop.models.DiscountModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class CRUDCoupon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_coupon);

        EditText couponName = findViewById(R.id.DiscountName);
        EditText couponCode = findViewById(R.id.DiscountCode);
        EditText couponDiscount = findViewById(R.id.DiscountPercentage);

        couponName.setHint("Coupon Name");
        couponCode.setHint("Coupon Code");
        couponDiscount.setHint("50");


        Button couponSubmit = findViewById(R.id.couponSubmit);
        couponSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = couponName.getText().toString();
                String code = couponCode.getText().toString();
                String discount = couponDiscount.getText().toString();
                Log.d("Coupon", "discount: " + discount);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Coupons").document().set(
                        new DiscountModel(name, code, Integer.parseInt(discount))
                );
            }
        });


    }


}