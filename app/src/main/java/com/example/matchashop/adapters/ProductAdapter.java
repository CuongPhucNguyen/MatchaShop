package com.example.matchashop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.R;
import com.example.matchashop.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<ProductModel> productModelArrayList;

    // creating a constructor for our variables.
    public ProductAdapter(ArrayList<ProductModel> courseModelArrayList, Context context) {
        this.productModelArrayList = courseModelArrayList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<ProductModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        productModelArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public void nullList(){
        productModelArrayList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        ProductModel model = productModelArrayList.get(position);
        holder.productNameTV.setText(model.getProductName());
        holder.productPriceTV.setText(Double.toString(model.getProductPrice()));
//        Picasso.get().load(model.getProductPhotoTitle()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return productModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private final TextView productNameTV;
        private final TextView productPriceTV;
        private final ImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            productNameTV = itemView.findViewById(R.id.idTVProductName);
            productPriceTV = itemView.findViewById(R.id.idTVProductPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
