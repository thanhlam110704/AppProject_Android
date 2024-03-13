package com.example.appproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appproject.R;
import com.example.appproject.db.ChapterDataHelper;
import com.example.appproject.db.MyDatabaseHelper;
import com.example.appproject.model.Chapter;
import com.example.appproject.model.Comic;
import com.example.appproject.user.DetailComicActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemSearchAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ChapterDataHelper chapterDataHelper;
    private int layout;
    private List<Comic> originalArrayList; // Original data
    private List<Comic> filteredArrayList; // Filtered data for search

    public ItemSearchAdapter(Context context, int layout, List<Comic> arrayList) {
        this.context = context;
        this.layout = layout;
        this.originalArrayList = arrayList;
        this.filteredArrayList = new ArrayList<>(arrayList);
    }

    @Override
    public int getCount() {
        return filteredArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(layout, null);
        }
        Comic comic = filteredArrayList.get(position);
        chapterDataHelper = new ChapterDataHelper(context);
        // Lấy chapter mới nhất của truyện
        Chapter chapter = chapterDataHelper.getLatestChapterByComicId(comic.getId());
        // Lấy danh sách thể loại của truyện
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
        List<String> genres = dbHelper.getGenresByComicId(comic.getId());
        byte[] imageByteArray = comic.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        // Hiển thị danh sách thể loại
        StringBuilder genreText = new StringBuilder();
        for (String genre : genres) {
            genreText.append(genre).append(", ");
        }
        // Xoá dấu "," cuối cùng nếu có
        if (genreText.length() > 0) {
            genreText.deleteCharAt(genreText.length() - 2);
        }

        TextView name_comic = convertView.findViewById(R.id.txtTenTruyen_search);
        TextView name_chapter = convertView.findViewById(R.id.txtTenChap_search);
        TextView genres_comic = convertView.findViewById(R.id.txtTheLoai_search);
        ImageView imageView = convertView.findViewById(R.id.imgTruyen_search);
        name_comic.setText(comic.getName());
        name_chapter.setText(chapter.getNameChap());
        genres_comic.setText(genreText.toString());
        imageView.setImageBitmap(bitmap);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailComicActivity.class);
                intent.putExtra("comic", (Serializable) comic);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Comic> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // No filter applied, show all items
                    filteredList.addAll(originalArrayList);
                } else {
                    // Filter items based on the search query
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Comic comic : originalArrayList) {
                        if (comic.getName().toLowerCase().contains(filterPattern)
                                || containsGenre(comic, filterPattern)) {
                            filteredList.add(comic);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredArrayList.clear();
                filteredArrayList.addAll((List<Comic>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    // Helper method to check if any genre of the comic contains the filter pattern
    private boolean containsGenre(Comic comic, String filterPattern) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
        List<String> genres = dbHelper.getGenresByComicId(comic.getId());

        for (String genre : genres) {
            if (genre.toLowerCase().contains(filterPattern)) {
                return true;
            }
        }

        // Also check if chapter name contains the filter pattern
        ChapterDataHelper chapterDataHelper = new ChapterDataHelper(context);
        Chapter latestChapter = chapterDataHelper.getLatestChapterByComicId(comic.getId());
        if (latestChapter != null && latestChapter.getNameChap().toLowerCase().contains(filterPattern)) {
            return true;
        }

        return false;
    }
}
