package com.example.appproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appproject.R;

import java.util.ArrayList;

public class ImageListAdapter extends ArrayAdapter<byte[]> {

    private Context mContext;
    private ArrayList<byte[]> mImageBytes;

    public ImageListAdapter(Context context, ArrayList<byte[]> imageBytes) {
        super(context, 0, imageBytes);
        mContext = context;
        mImageBytes = imageBytes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_image, parent, false);
        }
        ImageView imageView = listItemView.findViewById(R.id.chapter_img_detailchapter);

        // Decode byte array to bitmap
        byte[] imageData = mImageBytes.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

        imageView.setImageBitmap(bitmap);
        return listItemView;
    }

}