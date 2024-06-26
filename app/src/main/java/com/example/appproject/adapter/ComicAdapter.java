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
import com.example.appproject.db.ChapterDataHelper;
import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;
import com.example.appproject.user.DetailComicActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.MyViewHolder> {
    private ChapterDataHelper chapterDataHelper;
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
        chapterDataHelper = new ChapterDataHelper(context);
        Chapter chapter = chapterDataHelper.getLatestChapterByComicId(comic.getId());
        holder.comic_name .setText(comic.getName());
        holder.chapter_name.setText(chapter.getNameChap());
        holder.chapter_dateupdate.setText(chapter.getDatePublish());
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
        private TextView comic_name,chapter_name,chapter_dateupdate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comic_name = itemView.findViewById(R.id.comic_name_txt_home);
            comic_avatar = itemView.findViewById(R.id.comic_avatar_img_home);
            chapter_name= itemView.findViewById(R.id.chapter_name_txt_home);
            chapter_dateupdate=itemView.findViewById(R.id.chapter_dateupdate_txt_home);

        }
    }
}
