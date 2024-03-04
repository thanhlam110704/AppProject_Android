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

import java.util.ArrayList;

public class CustomComicAdapter extends RecyclerView.Adapter<CustomComicAdapter.MyViewHolder> {
    Activity activity;

    private Context context;
    private ArrayList comic_id, comic_name, comic_author, comic_description, comic_status, comic_dateupdate;
    private ArrayList<byte[]> comic_avatar;
    public CustomComicAdapter(Activity activity, Context context,
                              ArrayList comic_id, ArrayList comic_name, ArrayList comic_author, ArrayList comic_description,
                              ArrayList comic_status, ArrayList comic_dateupdate, ArrayList<byte[]> comic_avatar){
        this.activity=activity;
        this.context=context;
        this.comic_id=comic_id;
        this.comic_name=comic_name;
        this.comic_description=comic_description;
        this.comic_status=comic_status;
        this.comic_author=comic_author;
        this.comic_dateupdate=comic_dateupdate;
        this.comic_avatar=comic_avatar;
    }


    @NonNull
    @Override
    public CustomComicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomComicAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.comic_id_txt.setText(String.valueOf(comic_id.get(position)));
        holder.comic_name_txt.setText(String.valueOf(comic_name.get(position)));
        holder.comic_author_txt.setText(String.valueOf(comic_author.get(position)));
        holder.comic_description_txt.setText(String.valueOf(comic_description.get(position)));
        holder.comic_status_txt.setText(String.valueOf(comic_status.get(position)));
        holder.comic_dateupdate_txt.setText(String.valueOf(comic_dateupdate.get(position)));
        byte[] avatarBytes = comic_avatar.get(position);
        if (avatarBytes != null) {
            Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
            holder.comic_avatar_img.setImageBitmap(avatarBitmap);
        } else {
            // Set a default image or placeholder if needed
            holder.comic_avatar_img.setImageResource(R.drawable.hinh1);
        }

        holder.mainLayout.setOnClickListener(v -> {
            Intent intent= new Intent((context), UpdateComicActivity.class);
            intent.putExtra("id",String.valueOf(comic_id.get(position)));
            intent.putExtra("name",String.valueOf(comic_name.get(position)));
            intent.putExtra("description",String.valueOf(comic_description.get(position)));
            intent.putExtra("author",String.valueOf(comic_author.get(position)));
            intent.putExtra("status",String.valueOf(comic_status.get(position)));
            intent.putExtra("dateupdate",String.valueOf(comic_dateupdate.get(position)));
            intent.putExtra("avatar", comic_avatar.get(position));

            activity.startActivityForResult(intent,1);
        });
    }


    @Override
    public int getItemCount() {
        return comic_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        ImageView comic_avatar_img;
        TextView comic_id_txt,comic_name_txt, comic_description_txt,comic_author_txt,comic_status_txt,comic_dateupdate_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comic_id_txt=itemView.findViewById(R.id.comic_id_txt);
            comic_name_txt=itemView.findViewById(R.id.comic_name_txt);
            comic_author_txt=itemView.findViewById(R.id.comic_author_txt);
            comic_description_txt=itemView.findViewById(R.id.comic_description_txt);
            comic_status_txt=itemView.findViewById(R.id.comic_status_txt);
            comic_dateupdate_txt=itemView.findViewById(R.id.comic_dateupdate_txt);
            comic_avatar_img=itemView.findViewById(R.id.comic_avatar_img);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
