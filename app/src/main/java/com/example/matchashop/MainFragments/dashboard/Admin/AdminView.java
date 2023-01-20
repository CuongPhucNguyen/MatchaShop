package com.example.matchashop.MainFragments.dashboard.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matchashop.MainFragments.dashboard.AddProducts.AddProduct;
import com.example.matchashop.MainFragments.dashboard.CouponForm.CreateCoupon;
import com.example.matchashop.MainFragments.dashboard.CouponForm.DeleteCoupon;
import com.example.matchashop.R;

public class AdminView extends AppCompatActivity {
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.admin_view);
            ImageView returnButton = findViewById(R.id.returnButton);
            returnButton.setOnClickListener(v -> {
                finish();
            });

            Button addProduct = findViewById(R.id.addProduct);
            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminView.this, AddProduct.class);
                    startActivity(intent);
                }
            });

            Button addCoupon = findViewById(R.id.addCoupon);
            addCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminView.this, CreateCoupon.class);
                    startActivity(intent);
                }
            });

            Button deleteCoupon = findViewById(R.id.deleteCoupon);
            deleteCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminView.this, DeleteCoupon.class);
                    startActivity(intent);
                }
            });
            
        }
}
