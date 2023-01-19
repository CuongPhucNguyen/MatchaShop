package com.example.matchashop.MainFragments.card;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchashop.BroadcastReceivers.SoundBroadcastReceivers;
import com.example.matchashop.MainActivity;
import com.example.matchashop.R;
import com.example.matchashop.Service.NotificationService;
import com.example.matchashop.adapters.OrderAdapter;
import com.example.matchashop.databinding.FragmentCardBinding;
import com.example.matchashop.models.DiscountModel;
import com.example.matchashop.models.OrderModel;
import com.example.matchashop.models.ProductModel;
import com.example.matchashop.models.UserModel;
import com.example.matchashop.models.productQuantity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;


public class CardFragment extends Fragment {
    public FragmentCardBinding binding;


    public View fragView;
    private FirebaseAuth mAuth;
    private RecyclerView orderRV;
    private boolean isDiscounted;
    private int discount;
    protected SoundBroadcastReceivers soundBroadcastReceiver;
    protected IntentFilter intentFilter;
    private void registerReceiver() {
        soundBroadcastReceiver = new SoundBroadcastReceivers();
        intentFilter = new IntentFilter(MainActivity.CHECKOUT_SOUND);
        intentFilter.addAction(MainActivity.COUPON_SOUND);
        this.getActivity().registerReceiver(soundBroadcastReceiver, intentFilter);
    }
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
        registerReceiver();
        Intent intent = new Intent(getContext(), NotificationService.class);
        getActivity().bindService(intent, connection, getActivity().BIND_AUTO_CREATE);
        getActivity().startService(intent);
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
                                                   orderList.add(productQuantity);

                                           }
                                       }
                                   }
                                   TextView textView = (TextView) binding.getRoot().findViewById(R.id.finalCost);
                                   String finalCost = textView.getText().toString();
                                   Double finalCostDouble = Double.parseDouble(finalCost);
                                   OrderModel orderModel = new OrderModel(orderList, finalCostDouble);
                                   userModel.setCart(new ArrayList<>());
                                   order.add(orderModel);
                                   userModel.setOrders(order);
                                   db.collection("users").document(uid).set(userModel);
                                   Toast.makeText(getContext(), "Order placed", Toast.LENGTH_SHORT).show();
                                   getActivity().sendBroadcast(new Intent(MainActivity.CHECKOUT_SOUND));
                                   notificationService.sendNotification("Order placed", "Your order at " + Calendar.getInstance().getTime() + " has been placed");
                                   buildRecyclerView();
                                   calculatePrice();

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

        Button couponBtn = (Button) binding.getRoot().findViewById(R.id.applyCoupon);
        couponBtn.setOnClickListener(v -> {
            applyDiscount();
        });
        calculatePrice();
        return fragView;
    }
    private void applyDiscount()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        EditText editText = (EditText) binding.getRoot().findViewById(R.id.couponCode);
        String code = editText.getText().toString();
        if(!code.isEmpty()) {
            db.collection("coupons").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            DiscountModel discountModel = document.toObject(DiscountModel.class);
                            if(discountModel.getDiscountCode().equals(code))
                            {
                                isDiscounted = true;
                                discount = discountModel.getDiscountPercentage();
                                Toast.makeText(getContext(), "Discount applied", Toast.LENGTH_SHORT).show();
                                calculatePrice();
                                getActivity().sendBroadcast(new Intent(MainActivity.COUPON_SOUND));
                                break;
                            }
                        }
                    }
                    if(!isDiscounted)
                    {
                        Toast.makeText(getContext(), "Invalid coupon code", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            });
        }
        else {
            Toast.makeText(getContext(), "Coupon code can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculatePrice() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    UserModel userModel = document.toObject(UserModel.class);
                    ArrayList<productQuantity> cart = (ArrayList<productQuantity>) userModel.getCart();
                    db.collection("products").get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            double price = 0;
                            for (DocumentSnapshot document1 : task1.getResult()) {
                                for (productQuantity productQuantity : cart) {
                                    if (productQuantity.getProductId().equals(document1.getId())) {
                                        ProductModel productModel = document1.toObject(ProductModel.class);
                                        price += productQuantity.getQuantity() * productModel.getProductPrice();
                                    }
                                }
                            }
                            TextView totalPrice = (TextView) binding.getRoot().findViewById(R.id.itemCosts);
                            totalPrice.setText("Total Price: " + price);
                            TextView finalCost = (TextView) binding.getRoot().findViewById(R.id.finalCost);
                            if(isDiscounted)
                            {
                                finalCost.setText(String.valueOf(price - (price * (discount * 0.01))));
                            }
                            else {
                                finalCost.setText(String.valueOf(price));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task1.getException());
                        }
                    });
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
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