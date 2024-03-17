package com.example.appproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.example.appproject.admin.UpdateComicActivity;
import com.example.appproject.db.ChapterDataHelper;
import com.example.appproject.model.Account;
import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;
import com.example.appproject.user.DetailComicActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomAccountAdapter extends RecyclerView.Adapter<CustomAccountAdapter.MyViewHolder>{
    List<Account> accountList;
    Context context;
    public CustomAccountAdapter (List<Account> accountList, Context context) {
        this.accountList= accountList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAccountAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_account, parent, false);
        return new CustomAccountAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.account_name .setText(account.getUsername());
        holder.account_role.setText(account.getRole());
        holder.account_phone.setText(account.getPhone());
        byte[] imageByteArray = account.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        holder.account_avatar.setImageBitmap(bitmap);
    }



    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView account_avatar;
        private TextView account_name,account_role,account_phone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            account_name = itemView.findViewById(R.id.account_name_txt);
            account_avatar= itemView.findViewById(R.id.account_avatar_img);
            account_role= itemView.findViewById(R.id.account_role_txt);
            account_phone=itemView.findViewById(R.id.account_phone_txt);

        }
    }
}
