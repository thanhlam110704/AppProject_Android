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
import com.example.appproject.db.AccountDataHelper;
import com.example.appproject.db.ChapterDataHelper;
import com.example.appproject.db.ComicDataHelper;
import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.db.SessionManager;
import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;
import com.example.appproject.model.Save;
import com.example.appproject.user.DetailComicActivity;

import java.io.Serializable;
import java.util.List;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.MyViewHolder>  {
    MyDatabaseHelper myDatabaseHelper;
    private ChapterDataHelper chapterDataHelper;
    SessionManager sessionManager;
    List<Comic> comicList;
    Context context;
    public SaveAdapter(Context context,  List<Comic> comicList ) {
        this.context = context;
        this.comicList= comicList;
    }

    @NonNull
    @Override
    public SaveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_save_account, parent, false);
        return new SaveAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveAdapter.MyViewHolder holder, int position) {
        if(comicList!=null) {
            Comic comic = comicList.get(position);
            myDatabaseHelper = new MyDatabaseHelper(context);
            chapterDataHelper = new ChapterDataHelper(context);
            Chapter chapter = chapterDataHelper.getLatestChapterByComicId(comic.getId());
            holder.comic_name.setText(comic.getName());
            holder.chapter_name.setText(chapter.getNameChap());
            holder.chapter_dateupdate.setText(chapter.getDatePublish());
            byte[] imageByteArray = comic.getAvatar();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            holder.comic_avatar.setImageBitmap(bitmap);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailComicActivity.class);
                    intent.putExtra("comic", comic);
                    context.startActivity(intent);
                }
            });
        }
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
            comic_name = itemView.findViewById(R.id.comic_name_txt_save);
            comic_avatar = itemView.findViewById(R.id.comic_avatar_img_save);
            chapter_name= itemView.findViewById(R.id.chapter_name_txt_save);
            chapter_dateupdate=itemView.findViewById(R.id.chapter_dateupdate_txt_save);

        }
    }
}
