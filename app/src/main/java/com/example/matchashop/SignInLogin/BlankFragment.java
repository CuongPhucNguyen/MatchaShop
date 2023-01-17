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

import androidx.fragment.app.Fragment;

import com.example.matchashop.MainActivity;
import com.example.matchashop.R;
import com.example.matchashop.Service.NotificationService;
import com.example.matchashop.managers.UserDatabaseManager;
import com.example.matchashop.models.User;

import java.sql.SQLException;


public class BlankFragment extends Fragment {
    private UserDatabaseManager dbManager;
    View fragView;
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
        dbManager = new UserDatabaseManager(fragView.getContext());

        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getContext(), NotificationService.class);
        getActivity().bindService(intent, connection, getActivity().BIND_AUTO_CREATE);
        getActivity().startService(intent);

        Button button = (Button) fragView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().sendBroadcast(new Intent(MainActivity.CLICK_SOUND));
                notificationService.sendNotification("Test", "Test");
                EditText name = fragView.findViewById(R.id.loginUsernameUser);
                EditText password = fragView.findViewById(R.id.loginPasswordUser);
                User newInput = new User(name.getText().toString(), password.getText().toString(), true, true);
                if (!UserDatabaseManager.checkIfExisted(dbManager.fetchByName(name.getText().toString()))) {
                    try {
                        Log.d("CREATION", "insert");
                        dbManager.insert(newInput);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Log.d("CREATION", "update " + dbManager.fetchByName(newInput.Username).getString(0));
                        dbManager.update(Long.parseLong(dbManager.fetchByName(newInput.Username).getString(0)),newInput);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(fragView.getContext(), BottomNavigationActivity.class);
                //attach some data to the intent
                intent.putExtra("message", "Hello bottom navigation activity");
                Log.println(Log.ASSERT, "test", "reach line 67");

                startActivity(intent);
            }
        });

        return fragView;
    }
}