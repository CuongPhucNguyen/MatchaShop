package com.example.matchashop;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.adapters.OrderRecyclerAdapter;
import com.example.matchashop.models.OrderModel;
import com.example.matchashop.models.productQuantity;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        RecyclerView
                ParentRecyclerViewItem
                = findViewById(
                R.id.parent_recyclerview);

        // Initialise the Linear layout manager
        LinearLayoutManager
                layoutManager
                = new LinearLayoutManager(
                OrderHistoryActivity.this);

        // Pass the arguments
        // to the parentItemAdapter.
        // These arguments are passed
        // using a method ParentItemList()
        OrderRecyclerAdapter
                parentItemAdapter
                = new OrderRecyclerAdapter(
                ParentItemList());

        // Set the layout manager
        // and adapter for items
        // of the parent recyclerview
        ParentRecyclerViewItem
                .setAdapter(parentItemAdapter);
        ParentRecyclerViewItem
                .setLayoutManager(layoutManager);
    }

    private List<OrderModel> ParentItemList()
    {
        List<OrderModel> itemList
                = new ArrayList<>();



        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<productQuantity> ChildItemList()
    {
        List<productQuantity> ChildItemList
                = new ArrayList<>();



        return ChildItemList;
    }
}
