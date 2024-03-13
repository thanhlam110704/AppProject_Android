package com.example.appproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appproject.R;
import com.example.appproject.db.ChapterDataHelper;
import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;
import com.example.appproject.user.DetailComicActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChapterLvDetailAdapter extends ArrayAdapter<Chapter> {
    private Context context;
    private List<Chapter> chapters;

    public ChapterLvDetailAdapter(Context context, List<Chapter> chapters) {
        super(context, R.layout.layout_item_chapter_detail, chapters);
        this.context = context;
        this.chapters = chapters;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_item_chapter_detail, parent, false);
        }

        Chapter chapter = chapters.get(position);
        TextView chapterName = convertView.findViewById(R.id.chapter_name_item_lv);
        TextView chapterView = convertView.findViewById(R.id.chapter_view_item_lv);

        chapterName.setText(chapter.getNameChap());
        chapterView.setText(chapter.getViewer());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailComicActivity.class);
                intent.putExtra("chapter", chapter); // Truyền đối tượng Chapter sang DetailComicActivity
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
