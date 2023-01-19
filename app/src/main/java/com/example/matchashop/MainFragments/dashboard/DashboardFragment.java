package com.example.matchashop.MainFragments.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.matchashop.MainFragments.dashboard.AddProducts.AddProduct;
import com.example.matchashop.MainActivity;
import com.example.matchashop.MainFragments.dashboard.CouponForm.CRUDCoupon;
import com.example.matchashop.OrderHistoryActivity;
import com.example.matchashop.R;
import com.example.matchashop.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.example.matchashop.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

//    private View binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        this.getActivity().setContentView(R.layout.fragment_dashboard);

        TextView textView = root.findViewById(R.id.userName);
        TextView textView1 = root.findViewById(R.id.userEmail);
        ImageView image = root.findViewById(R.id.userImage);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        Button addProduct = root.findViewById(R.id.addProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProduct.class);
                startActivity(intent);
            }
        });

        Button addCoupon = root.findViewById(R.id.addCoupon);
        addCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CRUDCoupon.class);
                startActivity(intent);
            }
        });


        Button myOrder = (Button) root.findViewById(R.id.myOrders);
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        db.collection("users").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel user = task.getResult().toObject(UserModel.class);
                textView.setText(user.getUsername());
                textView1.setText(user.getEmail());
                Picasso.get().load(user.getUserImgURL()).into(image);
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