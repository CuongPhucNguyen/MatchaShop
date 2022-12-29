package com.example.matchashop.User.UserListCard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchashop.R;
import com.example.matchashop.User.User;

import java.util.ArrayList;

public class RecyclerViewUser extends RecyclerView.Adapter<RecyclerViewUser.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context contextRecycler;
    ArrayList<User> listRecycler;

    public RecyclerViewUser(Context context, ArrayList<User> list, RecyclerViewInterface recyclerViewInterface){
        this.contextRecycler = context;
        this.listRecycler = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(contextRecycler);
        View view = inflator.inflate(R.layout.task_view, parent, false);
        return new MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(listRecycler.get(position).Username);
        holder.start.setText(listRecycler.get(position).Password);
        holder.end.setText(String.valueOf(listRecycler.get(position).savePassword));
    }

    @Override
    public int getItemCount() {
        return listRecycler.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, start, end, overDue;
        Button done;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            start = itemView.findViewById(R.id.startCard);
            end = itemView.findViewById(R.id.endCard);
        }

    }
}
