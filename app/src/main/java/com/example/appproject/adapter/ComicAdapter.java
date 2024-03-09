package com.example.appproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.example.appproject.model.Comic;
import com.example.appproject.user.DetailComicActivity;

import java.io.Serializable;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.MyViewHolder> {

    List<Comic> comicList;
    Context context;
    public ComicAdapter(List<Comic> comicList, Context context) {
        this.comicList= comicList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_truyendecu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicAdapter.MyViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        holder.comic_name .setText(comic.getName());

        // Hiển thị hình ảnh từ mảng byte (BLOB)
        byte[] imageByteArray = comic.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        holder.comic_avatar.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailComicActivity.class);
                intent.putExtra("comic", (Serializable) comic);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView comic_avatar;
        private TextView comic_name;
        private TextView txtTenChap;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comic_name = itemView.findViewById(R.id.comic_name_txt_home);
            comic_avatar = itemView.findViewById(R.id.comic_avatar_img_home);
        }
    }
}
