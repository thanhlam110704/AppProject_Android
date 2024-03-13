package com.example.appproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appproject.R;
import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;
import com.example.appproject.user.DetailChapterActivity;

import java.util.List;

public class ChapterLvDetailAdapter extends ArrayAdapter<Chapter> {
    private Context context;
    private List<Chapter> chapters;
    private Comic comic; // Thêm biến để lưu Comic

    public ChapterLvDetailAdapter(Context context, List<Chapter> chapters, Comic comic) {
        super(context, R.layout.layout_item_chapter_detail, chapters);
        this.context = context;
        this.chapters = chapters;
        this.comic = comic; // Lưu Comic từ Intent
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
                Intent intent = new Intent(context, DetailChapterActivity.class);
                intent.putExtra("chapter", chapter); // Truyền đối tượng Chapter sang DetailChapterActivity
                intent.putExtra("comic", comic);
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
                dbHelper.incrementViewer(chapter.getIdChapter(),comic.getId());// Truyền đối tượng Comic sang DetailChapterActivity
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
