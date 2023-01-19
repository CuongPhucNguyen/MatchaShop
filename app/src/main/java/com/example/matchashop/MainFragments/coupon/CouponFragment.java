package com.example.matchashop.MainFragments.coupon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.R;
import com.example.matchashop.adapters.CouponAdapter;
import com.example.matchashop.databinding.FragmentNotificationsBinding;
import com.example.matchashop.models.DiscountModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class CouponFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView couponRV;
    ArrayList<DiscountModel> couponArrayList;
    ArrayList<String> couponIdList;
    FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CouponViewModel notificationsViewModel =
                new ViewModelProvider(this).get(CouponViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        db = FirebaseFirestore.getInstance();
        db.collection("coupons").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Log.d("TAG", document.getId() + " => " + document.getData());
                    couponArrayList.add(document.toObject(DiscountModel.class));
                    couponIdList.add(document.getId());
                    couponRV = root.findViewById(R.id.couponDeleteList);
                }

                CouponAdapter adapter = new CouponAdapter(couponArrayList, db, couponIdList);
                couponRV = root.findViewById(R.id.couponList);
                couponRV.setAdapter(adapter);
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}