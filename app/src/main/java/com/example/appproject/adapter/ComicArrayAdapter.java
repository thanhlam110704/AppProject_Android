package com.example.appproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.appproject.R;
import com.example.appproject.model.Comic;
import com.example.appproject.model.TruyenTranh;
import com.example.appproject.user.DetailComicActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComicArrayAdapter extends ArrayAdapter<Comic> {

    private Context ct;
    private ArrayList<Comic> comics;

    public ComicArrayAdapter(@NonNull Context context, int resource, @NonNull List<Comic> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.comics = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen, null);
        }
        if(comics.size()>0){
            Comic comic= this.comics.get(position);
            byte[] imageByteArray = comic.getAvatar();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            TextView name_comic_arr = convertView.findViewById(R.id.txtTenTruyen);
            TextView name_chapter_arr= convertView.findViewById(R.id.txtTenChap);
            ImageView img_comic_arr= convertView.findViewById(R.id.imgAnhTruyen);
            name_comic_arr.setText(comic.getName());
            //name_chapter_arr.setText(comic.getTenChap());
            img_comic_arr.setImageBitmap(bitmap);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ct, DetailComicActivity.class);
                    intent.putExtra("comic", (Serializable) comic); // Truyền dữ liệu cần thiết, có thể là ID của truyện, chẳng hạn
                    ct.startActivity(intent);
                }
            });
        }
        return convertView;
    }


}
