package com.example.appproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appproject.R;

import java.util.List;

public class ChapterAdapter extends BaseAdapter {
    private Context context;
    private List<String> chapters;

    public ChapterAdapter(Context context, List<String> chapters) {
        this.context = context;
        this.chapters = chapters;
    }

    @Override
    public int getCount() {
        return chapters.size();
    }

    @Override
    public Object getItem(int position) {
        return chapters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_chapter, parent, false);
        }

        // Lấy TextView từ custom layout
        TextView textViewChapterName = convertView.findViewById(R.id.textViewChapterName);

        // Lấy dữ liệu từ danh sách chapters
        String chapterName = chapters.get(position);

        // Hiển thị dữ liệu trong TextView
        textViewChapterName.setText(chapterName);

        return convertView;
    }
}

