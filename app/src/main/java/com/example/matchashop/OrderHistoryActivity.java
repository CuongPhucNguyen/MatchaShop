package com.example.matchashop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.adapters.OrderRecyclerAdapter;
import com.example.matchashop.models.OrderModel;
import com.example.matchashop.models.UserModel;
import com.example.matchashop.models.productQuantity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {


    List<OrderModel> itemList;
    List<productQuantity> childItemList;
    RecyclerView recyclerView;
    OrderRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ImageView back = findViewById(R.id.returnButton);
        back.setOnClickListener(v -> finish());

        RecyclerView
                ParentRecyclerViewItem
                = findViewById(
                R.id.parent_recyclerview);

        // Initialise the Linear layout manager
        LinearLayoutManager
                layoutManager
                = new LinearLayoutManager(
                OrderHistoryActivity.this);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();


        db.collection("users").document(uid).get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                UserModel user = task1.getResult().toObject(UserModel.class);


                assert user != null;
                // Pass the arguments
                // to the parentItemAdapter.
                // These arguments are passed
                // using a method ParentItemList()
                OrderRecyclerAdapter
                        parentItemAdapter
                        = new OrderRecyclerAdapter(user.getOrders());


                // Set the layout manager
                // and adapter for items
                // of the parent recyclerview
                ParentRecyclerViewItem
                        .setAdapter(parentItemAdapter);
                ParentRecyclerViewItem
                        .setLayoutManager(layoutManager);
            }
        });


    }

    private List<OrderModel> ParentItemList() {


        return new ArrayList<>();

    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<productQuantity> ChildItemList() {
        List<productQuantity> ChildItemList
                = new ArrayList<>();


        return ChildItemList;
    }
}
