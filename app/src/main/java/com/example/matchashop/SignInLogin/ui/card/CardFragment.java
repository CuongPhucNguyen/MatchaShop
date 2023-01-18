package com.example.matchashop.SignInLogin.ui.card;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.matchashop.R;
import com.example.matchashop.Service.NotificationService;
import com.example.matchashop.adapters.OrderAdapter;
import com.example.matchashop.databinding.FragmentCardBinding;
import com.example.matchashop.models.OrderModel;
import com.example.matchashop.models.ProductModel;
import com.example.matchashop.models.UserModel;
import com.example.matchashop.models.productQuantity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class CardFragment extends Fragment {
    public FragmentCardBinding binding;


    public View fragView;
    private FirebaseAuth mAuth;
    private RecyclerView orderRV;
    private boolean isDiscounted;
    private int discount;

    public ArrayList<productQuantity> productQuantityArrayList;
    public OrderAdapter adapter;

    private boolean serviceConnected = false;
    private NotificationService notificationService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NotificationService.NotificationBinder binder = (NotificationService.NotificationBinder) service;
            notificationService = binder.getService();
            serviceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceConnected = false;
        }

    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCardBinding.inflate(inflater, container, false);
        fragView = binding.getRoot();
        CardViewModel orderViewModel = new ViewModelProvider(this).get(CardViewModel.class);


        orderRV = binding.getRoot().findViewById(R.id.orderRecyclerView);


        mAuth = FirebaseAuth.getInstance();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("TAG", task.getResult().toString());
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });

        buildRecyclerView();
        Button button = (Button) binding.getRoot().findViewById(R.id.purchaseButton);

        button.setOnClickListener(v -> {
           db.collection("users").document(uid).get().addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {
                       Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                       UserModel userModel = document.toObject(UserModel.class);
                       if(userModel.getCart().size() == 0)
                       {
                           Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           ArrayList<productQuantity> cart = (ArrayList<productQuantity>) userModel.getCart();
                           ArrayList<OrderModel> order = (ArrayList<OrderModel>) userModel.getOrders();
                           db.collection("products").get().addOnCompleteListener(task1 -> {
                               if (task1.isSuccessful()) {
                                   double price = 0;
                                   ArrayList<productQuantity> orderList = new ArrayList<>();
                                   for (DocumentSnapshot document1 : task1.getResult()) {
                                       for (productQuantity productQuantity : cart) {
                                           if (productQuantity.getProductId().equals(document1.getId())) {
                                                  ProductModel productModel = document1.toObject(ProductModel.class);
                                                   price += productQuantity.getQuantity() * productModel.getProductPrice();
                                                   orderList.add(productQuantity);

                                           }
                                       }
                                   }
                                   OrderModel orderModel = new OrderModel(orderList, price);
                                   userModel.setCart(new ArrayList<>());
                                   order.add(orderModel);
                                   userModel.setOrders(order);
                                   db.collection("users").document(uid).set(userModel);
                                   Toast.makeText(getContext(), "Order placed", Toast.LENGTH_SHORT).show();
                               } else {
                                   Log.d("TAG", "Error getting documents: ", task1.getException());
                               }
                           });
                       }
                       adapter.notifyDataSetChanged();
                   } else {
                       Log.d("TAG", "No such document");
                   }
               } else {
                   Log.d("TAG", "get failed with ", task.getException());
               }
           });
        });
        return fragView;
    }

    private void calculatePrice() {

    }



    private void buildRecyclerView() {

        // below line we are creating a new array list
        productQuantityArrayList = new ArrayList<productQuantity>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        ArrayList<productQuantity> quantityList = new ArrayList<>();
        db.collection("users").document(uid).get().addOnCompleteListener(task -> {


            if (task.isSuccessful()) {


                DocumentSnapshot documentSnapshot = task.getResult();
                Log.d("TAG", documentSnapshot.get("cart").toString());
//                ArrayList<Map<String,Integer>> list = (ArrayList<Map<String,Integer>>) documentSnapshot.get("cart");
                UserModel userModel = documentSnapshot.toObject(UserModel.class);
//                int i = 0;
//                for ( productQuantity productQuantity : userModel.getCart()) {
//                    String productId = productQuantity.getProductId();
//                    int quantity = productQuantity.getQuantity();
//
//
//                    productQuantityArrayList.add(new productQuantity(productId, quantity));
//                    Log.d("TAG", productQuantityArrayList.get(i).getProductId());
//                    Log.d("TAG", productQuantityArrayList.get(i).getQuantity() + "");
//                    i++;
//                }
                productQuantityArrayList = (ArrayList<productQuantity>) userModel.getCart();


                // initializing our adapter class.
                adapter = new OrderAdapter(this.getContext(), productQuantityArrayList);

                // setting layout manager for our recycler view.
                orderRV.setLayoutManager(new LinearLayoutManager(this.getContext()));

                // setting adapter to our recycler view.
                orderRV.setAdapter(adapter);
                Log.d("TAG", "OrderFragment: got to 133");
                Log.d("TAG", productQuantityArrayList.size() + "");




            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });

    }


}