package com.example.matchashop.SignInLogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.matchashop.R;
import com.example.matchashop.User.DBUserHelper;
import com.example.matchashop.User.User;

import java.sql.SQLException;


public class BlankFragment extends Fragment {
    private DBUserHelper dbManager;
    View fragView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView =  inflater.inflate(R.layout.fragment_blank,container,false);
        dbManager = new DBUserHelper(fragView.getContext());

        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button button = (Button) fragView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = fragView.findViewById(R.id.loginUsernameUser);
                EditText password = fragView.findViewById(R.id.loginPasswordUser);
                User newInput = new User(name.getText().toString(), password.getText().toString(), true, true);
                if (!DBUserHelper.checkIfExisted(dbManager.fetchByName(name.getText().toString()))) {
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