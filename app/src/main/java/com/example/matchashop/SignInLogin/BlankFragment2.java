package com.example.matchashop.SignInLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.matchashop.R;
import com.example.matchashop.models.OrderModel;
import com.example.matchashop.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BlankFragment2 extends Fragment {
    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    EditText name;
    Button register;
    View signUpView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signUpView =  inflater.inflate(R.layout.fragment_blank2, container, false);

        email = (EditText) signUpView.findViewById(R.id.regEmail);
        password = (EditText) signUpView.findViewById(R.id.regPassword);
        name = (EditText) signUpView.findViewById(R.id.regName);

        mAuth = FirebaseAuth.getInstance();
        register = (Button) signUpView.findViewById(R.id.regButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String nameText = name.getText().toString();
                if (emailText.isEmpty() || passwordText.isEmpty() || nameText.isEmpty()) {
                    Toast.makeText(signUpView.getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(signUpView.getContext(), "User Created", Toast.LENGTH_SHORT).show();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("users").document(mAuth.getCurrentUser().getUid()).set(new UserModel(nameText, emailText,
                                    "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Twemoji_1f600.svg/1200px-Twemoji_1f600.svg.png",
                                    new ArrayList<OrderModel>()));
                        } else {
                            Toast.makeText(signUpView.getContext(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



        return signUpView;

    }
}