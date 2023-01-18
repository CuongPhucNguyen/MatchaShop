package com.example.matchashop;

import android.content.Intent;
import android.os.Bundle;

import com.example.matchashop.SignInLogin.BottomNavigationActivity;
import com.example.matchashop.SignInLogin.ui.home.HomeFragment;
import com.example.matchashop.SignInLogin.ui.notifications.NotificationsFragment;
import com.example.matchashop.SignInLogin.ui.order.OrderFragment;
import com.example.matchashop.databinding.FragmentOrderBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.matchashop.ui.main.SectionsPagerAdapter;
import com.example.matchashop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String CLICK_SOUND = "CLICK";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = binding.viewPager;

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



            }
        });

    }



}