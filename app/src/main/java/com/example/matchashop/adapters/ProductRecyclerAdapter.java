package com.example.matchashop.adapters;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matchashop.R;
import com.example.matchashop.models.ProductModel;
import com.example.matchashop.models.productQuantity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductRecyclerAdapter
        extends RecyclerView
        .Adapter<ProductRecyclerAdapter.ChildViewHolder> {

    private List<productQuantity> ChildItemList;

    // Constructor
    ProductRecyclerAdapter(List<productQuantity> childItemList)
    {
        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.ordered_item_one,
                        viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChildViewHolder childViewHolder,
            int position)
    {

        // Create an instance of the ChildItem
        // class for the given position
        productQuantity childItem
                = ChildItemList.get(position);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").document(childItem.getProductId()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ProductModel item = task.getResult().toObject(ProductModel.class);
                if(item != null) {
                    childViewHolder
                            .ChildItemTitle
                            .setText(item.getProductName());

                    childViewHolder
                            .ChildItemPrice
                            .setText(String.valueOf(item.getProductPrice() * childItem.getQuantity()));

                    childViewHolder
                            .ChildItemQuantity
                            .setText(String.valueOf(item.getProductPrice()));


                    Picasso.get().load(item.getProductPhotoTitle()).into(childViewHolder.ChildItemImage);
                }



            }
        });






    }

    @Override
    public int getItemCount()
    {

        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return ChildItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ChildViewHolder
            extends RecyclerView.ViewHolder {

        TextView ChildItemTitle;
        TextView ChildItemPrice;
        TextView ChildItemQuantity;
        ImageView ChildItemImage;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            ChildItemTitle = itemView.findViewById(R.id.orderedProductName);
            ChildItemPrice = itemView.findViewById(R.id.orderedProductPrice);
            ChildItemQuantity = itemView.findViewById(R.id.orderedProductQuantity);
            ChildItemImage = itemView.findViewById(R.id.orderedProductImage);
        }
    }
}
