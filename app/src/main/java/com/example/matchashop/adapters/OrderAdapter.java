package com.example.matchashop.adapters;

import android.content.Context;
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
import com.example.matchashop.models.OrderModel;
import com.example.matchashop.models.ProductModel;
import com.example.matchashop.models.UserModel;
import com.example.matchashop.models.productQuantity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<productQuantity> productQuantityArrayList;
    private Context context;


    public OrderAdapter(Context context, ArrayList<productQuantity> productQuantityArrayList) {
        this.context = context;
        this.productQuantityArrayList = productQuantityArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card, parent, false);
        return new ViewHolder(view, productQuantityArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Log.d("TAG", document.getId() + " => " + document.getData());
                    Log.d("TAG", "POSITION <==@@@ " + productQuantityArrayList.get(position).getProductId());
                    if (document.getId().equals(productQuantityArrayList.get(position).getProductId())) {
                        ProductModel productModel = document.toObject(ProductModel.class);
                        Log.d("TAG", "PRODUCT MODEL <==@@@ " + productModel.getProductName());


                        holder.productName.setText(productModel.getProductName());
                        holder.productPrice.setText(String.valueOf(productModel.getProductPrice() * productQuantityArrayList.get(position).getQuantity()));
                        holder.productQuantity.setText(String.valueOf(productQuantityArrayList.get(position).getQuantity()));
                        Picasso.get().load(productModel.getProductPhotoTitle()).into(holder.productImage);
                        holder.removeBtn.setOnClickListener(v -> {
                            String uid = firebaseAuth.getCurrentUser().getUid();
                            db.collection("users").document(uid).get().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    UserModel user = task1.getResult().toObject(UserModel.class);
                                    for (int i = 0; i < user.getCart().size(); i++) {
                                        if (user.getCart().get(i).getProductId().equals(productQuantityArrayList.get(position).getProductId())) {
                                            user.getCart().remove(i);
                                            db.collection("users").document(uid).set(user);
                                            productQuantityArrayList.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    }

                                }
                            });
                        });
                    }
                }
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
        // on below line we are setting data to our text view and image view.

    }

    @Override
    public int getItemCount() {
        return productQuantityArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView productName;
        private final TextView productPrice;
        private final TextView productQuantity;
        private final ImageView productImage;
        private final ImageView addBtn;
        private final ImageView subtractBtn;
        private final Button removeBtn;

        public ViewHolder(@NonNull View itemView, ArrayList<productQuantity> productQuantityArrayList) {
            super(itemView);

            Log.d("TAG", "Order RV executing");

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.orderProductName);
            productPrice = itemView.findViewById(R.id.orderProductPrice);
            productQuantity = itemView.findViewById(R.id.orderQuantity);
            addBtn = itemView.findViewById(R.id.addQuantity);
            subtractBtn = itemView.findViewById(R.id.subtractQuantity);
            removeBtn = itemView.findViewById(R.id.removeFromCart);

//            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("products").get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    for (DocumentSnapshot document : task.getResult()) {
//                        if (document.getId() == productQuantityArrayList.get(getAdapterPosition()).getProductId()) {
//                            productName.setText(document.getString("name"));
//                            productPrice.setText(String.valueOf(Long.parseLong(
//                                    (
//                                            (document.getString("price") == null) ? "0" : document.getString("price")
//                                    )
//                            ) * productQuantityArrayList.get(position).getQuantity()));
//                            productQuantity.setText(productQuantityArrayList.get(position).getQuantity());
//                            Picasso.get().load(document.getString("image")).into(productImage);
//                        }
//                    }
//                } else {
//                    Log.d("TAG", "Error getting documents: ", task.getException());
//                }
//            });
        }
    }
}
