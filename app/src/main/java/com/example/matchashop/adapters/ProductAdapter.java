package com.example.matchashop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.R;
import com.example.matchashop.models.ProductModel;
import com.example.matchashop.models.UserModel;
import com.example.matchashop.models.productQuantity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<ProductModel> productModelArrayList;
    private Context context;
    private ArrayList<String> productRef;

    // creating a constructor for our variables.
    public ProductAdapter(ArrayList<ProductModel> courseModelArrayList, Context context, ArrayList<String> productRef) {
        this.productModelArrayList = courseModelArrayList;
        this.context = context;
        this.productRef = productRef;
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
        return new ViewHolder(view, productModelArrayList, productRef);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        ProductModel model = productModelArrayList.get(position);
        holder.productNameTV.setText(model.getProductName());
        holder.productPriceTV.setText(Double.toString(model.getProductPrice()));
        Picasso.get().load(model.getProductPhotoTitle()).into(holder.productImage);
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
        private final ImageView productAddCart;
        private final ImageView productAddQuantity;
        private final ImageView productSubtractQuantity;
        private int productQuantity = 1;
        private final TextView productQuantityDisplay;
        public ViewHolder(@NonNull View itemView, ArrayList<ProductModel> productModelArrayList, ArrayList<String> productRef) {
            super(itemView);
            // initializing our views with their ids.
            productNameTV = itemView.findViewById(R.id.idTVProductName);
            productPriceTV = itemView.findViewById(R.id.idTVProductPrice);
            productImage = itemView.findViewById(R.id.productImage);
            productAddCart = itemView.findViewById(R.id.addToCart);
            productAddQuantity = itemView.findViewById(R.id.addQuantity);
            productSubtractQuantity = itemView.findViewById(R.id.subtractQuantity);
            productQuantityDisplay = itemView.findViewById(R.id.quantity);

            productAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ProductModel clickedItem = productModelArrayList.get(position);
                        com.example.matchashop.models.productQuantity product = new productQuantity(productRef.get(position), productQuantity);
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        String uid = firebaseAuth.getCurrentUser().getUid();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(uid).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                UserModel userModel = task.getResult().toObject(UserModel.class);
                                if (userModel != null) {
                                    ArrayList<productQuantity> cart = (ArrayList<productQuantity>) userModel.getCart();
                                    if (cart == null) {
                                        cart = new ArrayList<>();
                                    }

                                    // check if product is already in cart
                                    boolean exist = false;
                                    int index = 0;
                                    for (int i = 0; i < cart.size() - 1; i++) {
                                        if(Objects.equals(cart.get(i).getProductId(), product.getProductId())){
                                            exist = true;
                                            index = i;
                                            break;
                                        }
                                    }

                                    // if product is already in cart, update quantity
                                    if (exist){
                                        cart.get(index).setQuantity(cart.get(index).getQuantity() + product.getQuantity());
                                    }


                                    // else add product to cart
                                    else {
                                        cart.add(product);
                                    }

                                    // update cart
                                    userModel.setCart(cart);
                                    db.collection("users").document(uid).set(userModel);
                                }
                            }
                        });
                        Toast.makeText(v.getContext(), "Added to cart" + clickedItem.getProductName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            productAddQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        productQuantity++;
                        productQuantityDisplay.setText(Integer.toString(productQuantity));
                    }
                }
            });

            productSubtractQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (productQuantity > 1) {
                            productQuantity--;
                            productQuantityDisplay.setText(Integer.toString(productQuantity));
                        }
                    }
                }
            });
        }
    }
}
