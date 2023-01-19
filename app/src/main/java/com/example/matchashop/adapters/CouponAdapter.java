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
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder executing");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_list_item, parent, false);
        return new CouponViewHolder(view, db, discountIdArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
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
        ImageView deleteCoupon;

        public CouponViewHolder(View itemView, FirebaseFirestore db, ArrayList<String> discountIdArrayList) {
            super(itemView);
            couponName = itemView.findViewById(R.id.couponName);
            couponCode = itemView.findViewById(R.id.couponCode);
            couponDiscount = itemView.findViewById(R.id.couponPercentage);
            deleteCoupon = itemView.findViewById(R.id.deleteCoupon);

            deleteCoupon.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        db.collection("coupons").document(discountIdArrayList.get(position)).delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                discountIdArrayList.remove(position);
                                discountModelArrayList.remove(position);
                                notifyItemRemoved(position);
                            }
                            else {
                                Log.d("TAG", "Failed to remove coupon");
                            }
                        });

                    }

                }
            });


        }
    }
}
