package com.example.matchashop.MainFragments.dashboard.AddProducts;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.matchashop.R;
import com.example.matchashop.models.ProductModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class AddProduct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        EditText productName = findViewById(R.id.addName);
        EditText productPrice = findViewById(R.id.addPrice);
        EditText productDescription = findViewById(R.id.addDescription);
        EditText productImage = findViewById(R.id.addImage);
        ImageView productImagePreview = findViewById(R.id.productPreview);
        Button submitProduct = findViewById(R.id.submitProduct);


        productName.setHint("Product Name");
        productPrice.setHint("Product Price");
        productDescription.setHint("Product Description");
        productImage.setHint("https://i.imgur.com/jSC0lG1.jpg");
        Picasso.get().load("https://i.imgur.com/jSC0lG1.jpg").into(productImagePreview);


        ImageView back = findViewById(R.id.returnButton);
        back.setOnClickListener(v -> finish());

        productImage.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    Picasso.get().load(productImage.getText().toString()).into(productImagePreview);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        submitProduct.setOnClickListener(v -> {

            if (productName.getText().toString().equals("") || productPrice.getText().toString().equals("") || productDescription.getText().toString().equals("") || productImage.getText().toString().equals("")) {
                productName.setHint("Please enter a product name");
                productPrice.setHint("Please enter a product price");
                productDescription.setHint("Please enter a product description");
                productImage.setHint("Please enter a product image");
            } else {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("products").document().set(new ProductModel(productName.getText().toString(), productImage.getText().toString(), Integer.parseInt(productPrice.getText().toString()),productDescription.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                });
            }

        });

    }
}