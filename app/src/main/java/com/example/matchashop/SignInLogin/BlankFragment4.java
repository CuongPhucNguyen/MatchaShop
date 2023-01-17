package com.example.matchashop.SignInLogin;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.R;
import com.example.matchashop.managers.UserDatabaseManager;
import com.example.matchashop.models.User;
import com.example.matchashop.User.UserListCard.RecyclerViewInterface;
import com.example.matchashop.User.UserListCard.RecyclerViewUser;

import java.sql.SQLException;
import java.util.ArrayList;

public class BlankFragment4 extends Fragment implements RecyclerViewInterface {
    UserDatabaseManager dbManager;
    View inflate;
    ArrayList<User> list = new ArrayList<>();
    RecyclerViewUser listRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_blank4, container, false);
        return inflate;
    }
    public void onResume(){
        super.onResume();
        try {
            dbManager = new UserDatabaseManager(inflate.getContext());
            dbManager.open();
            Cursor entries = dbManager.fetch();
            list.clear();
            for (int i = 0; i < entries.getCount(); i++){
                User item = new User(entries.getString(1),entries.getString(2),Boolean.parseBoolean(entries.getString(3)),Boolean.parseBoolean(entries.getString(4)));
                list.add(item);
                entries.moveToNext();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RecyclerView taskList = inflate.findViewById(R.id.userList);
        listRecycler = new RecyclerViewUser(inflate.getContext(), list, this);
        taskList.setAdapter(listRecycler);
        taskList.setLayoutManager(new LinearLayoutManager(inflate.getContext()));
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void editClick(int position) {

    }
}