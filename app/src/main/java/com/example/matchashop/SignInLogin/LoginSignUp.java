package com.example.matchashop.SignInLogin;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.matchashop.BroadcastReceivers.SoundBroadcastReceivers;
import com.example.matchashop.MainActivity;
import com.example.matchashop.R;
import com.example.matchashop.managers.UserDatabaseManager;
import com.example.matchashop.models.User;
import com.example.matchashop.User.UserListCard.RecyclerViewInterface;
import com.example.matchashop.User.UserListCard.RecyclerViewUser;
import com.google.android.material.tabs.TabLayout;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginSignUp extends AppCompatActivity implements RecyclerViewInterface {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    VPAdapter vpAdapter;
    private UserDatabaseManager dbManager;
    RecyclerViewUser listRecycler;
    ArrayList<User> list = new ArrayList<>();
    protected SoundBroadcastReceivers soundBroadcastReceiver;
    protected IntentFilter intentFilter;
    private void registerReceiver() {
        soundBroadcastReceiver = new SoundBroadcastReceivers();
        intentFilter = new IntentFilter(MainActivity.CLICK_SOUND);
        this.registerReceiver(soundBroadcastReceiver, intentFilter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            dbManager = new UserDatabaseManager(this);
            dbManager.open();
            registerReceiver();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_sign_up);
            tabLayout = findViewById(R.id.tabLayout);
            viewPager2 = findViewById(R.id.viewPager);
            vpAdapter = new VPAdapter(this);
            viewPager2.setAdapter(vpAdapter);


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager2.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    tabLayout.getTabAt(position).select();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void editClick(int position) {

    }
}