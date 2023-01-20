package com.example.matchashop.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.R;
import com.example.matchashop.models.DiscountModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {


    private final FirebaseFirestore db;
    private ArrayList<DiscountModel> discountModelArrayList;
    private ArrayList<String> discountIdArrayList;

    public CouponAdapter(ArrayList<DiscountModel> discountModelArrayList, FirebaseFirestore db, ArrayList<String> discountIdArrayList) {
        Log.d("TAG", "CouponAdapter executing");
        this.discountModelArrayList = discountModelArrayList;
        this.db = db;
        this.discountIdArrayList = discountIdArrayList;
    }


    @NonNull
    @Override
    public com.example.matchashop.adapters.CouponAdapter.CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder executing");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_coupon, parent, false);
        return new com.example.matchashop.adapters.CouponAdapter.CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.matchashop.adapters.CouponAdapter.CouponViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder executing");
        holder.couponCode.setText(discountModelArrayList.get(position).getDiscountCode());
        holder.couponDiscount.setText(String.valueOf(discountModelArrayList.get(position).getDiscountPercentage()));
        holder.couponName.setText(discountModelArrayList.get(position).getDiscountName());

    }


    @Override
    public int getItemCount() {
        return discountModelArrayList.size();
    }

    class CouponViewHolder extends RecyclerView.ViewHolder {
        TextView couponName;
        TextView couponCode;
        TextView couponDiscount;

        public CouponViewHolder(View itemView) {
            super(itemView);
            couponName = itemView.findViewById(R.id.couponName);
            couponCode = itemView.findViewById(R.id.couponCode);
            couponDiscount = itemView.findViewById(R.id.couponPercentage);
        }
    }


}
