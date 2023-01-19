package com.example.matchashop.MainFragments.dashboard.CouponForm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.matchashop.R;
import com.example.matchashop.adapters.CouponAdapter;
import com.example.matchashop.adapters.OrderAdapter;
import com.example.matchashop.adapters.ProductAdapter;
import com.example.matchashop.models.DiscountModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DeleteCoupon extends AppCompatActivity {


    ArrayList<DiscountModel> discountModelArrayList = new ArrayList<>();
    ArrayList<String> discountId = new ArrayList<>();
    private RecyclerView couponRV;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_list);

        db = FirebaseFirestore.getInstance();
        db.collection("coupons").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Log.d("TAG", document.getId() + " => " + document.getData());
                    discountModelArrayList.add(document.toObject(DiscountModel.class));
                    discountId.add(document.getId());
                    couponRV = findViewById(R.id.couponList);
                    buildCouponRecyclerView();
                }
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });


    }

    public void buildCouponRecyclerView(){


        CouponAdapter adapter = new CouponAdapter(discountModelArrayList, db, discountId);
        Log.d("TAG", "Building executing");
        couponRV.setAdapter(adapter);
        couponRV.setLayoutManager(new LinearLayoutManager(this));
    }
}