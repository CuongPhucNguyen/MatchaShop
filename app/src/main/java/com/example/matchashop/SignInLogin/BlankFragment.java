package com.example.matchashop.SignInLogin;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.matchashop.MainActivity;
import com.example.matchashop.R;
import com.example.matchashop.Service.NotificationService;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.SQLException;


public class BlankFragment extends Fragment {
    View fragView;
    private FirebaseAuth mAuth;

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
        fragView =  inflater.inflate(R.layout.fragment_blank,container,false);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(getContext(), NotificationService.class);
        getActivity().bindService(intent, connection, getActivity().BIND_AUTO_CREATE);
        getActivity().startService(intent);

        Button button = (Button) fragView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().sendBroadcast(new Intent(MainActivity.CLICK_SOUND));
                EditText name = fragView.findViewById(R.id.loginUsernameUser);
                EditText password = fragView.findViewById(R.id.loginPasswordUser);
                mAuth.signInWithEmailAndPassword(name.getText().toString(), password.getText().toString()).addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d("CREATION", "signInWithEmail:success");
                        Intent intent = new Intent(fragView.getContext(), BottomNavigationActivity.class);
                        startActivity(intent);
                    } else {
                        Log.w("CREATION", "signInWithEmail:failure", task.getException());
                        Toast.makeText(fragView.getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return fragView;
    }
}