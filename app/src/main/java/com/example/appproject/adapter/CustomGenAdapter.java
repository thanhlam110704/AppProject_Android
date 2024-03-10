package com.example.appproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.example.appproject.admin.UpdateGenActivity;

import java.util.ArrayList;

public class CustomGenAdapter extends RecyclerView.Adapter<CustomGenAdapter.MyViewHolder>{
    Activity activity;
    private Context context;
    private ArrayList genre_id,genre_name;
    public CustomGenAdapter(Activity activity, Context context,
                            ArrayList genre_id, ArrayList genre_name){
        this.activity=activity;
        this.context=context;
        this.genre_id=genre_id;
        this.genre_name=genre_name;
    }


    @NonNull
    @Override
    public CustomGenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row_gen,parent,false);
        return new CustomGenAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomGenAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.genre_id_txt.setText(String.valueOf(genre_id.get(position)));
        holder.genre_name_txt.setText(String.valueOf(genre_name.get(position)));
        holder.mainLayout3.setOnClickListener(v -> {
            Intent intent= new Intent((context), UpdateGenActivity.class);
            intent.putExtra("id",String.valueOf(genre_id.get(position)));
            intent.putExtra("genre",String.valueOf(genre_name.get(position)));
            activity.startActivityForResult(intent,1);
        });
    }


    @Override
    public int getItemCount() {
        return genre_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout3;
        TextView genre_id_txt,genre_name_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            genre_id_txt=itemView.findViewById(R.id.genre_id_txt);
            genre_name_txt=itemView.findViewById(R.id.genre_name_txt);
            mainLayout3=itemView.findViewById(R.id.mainLayout3);
        }
    }
}