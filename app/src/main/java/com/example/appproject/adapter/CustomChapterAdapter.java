package com.example.appproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.example.appproject.admin.UpdateChapterActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomChapterAdapter extends RecyclerView.Adapter<CustomChapterAdapter.MyViewHolder> {
    Activity activity;
    private Context context;
    private ArrayList chapter_id ,chapter_name,chapter_viewer,chapter_datepulish,chapter_idcomic;
    private ArrayList<List<byte[]>> chapter_img;
    public CustomChapterAdapter(Activity activity, Context context,
                                ArrayList chapter_id, ArrayList chapter_name, ArrayList chapter_viewer, ArrayList chapter_datepulish, ArrayList<List<byte[]>> chapter_img, ArrayList chapter_idcomic){
        this.activity=activity;
        this.context=context;
        this.chapter_id=chapter_id;
        this.chapter_name=chapter_name;
        this.chapter_viewer=chapter_viewer;
        this.chapter_datepulish=chapter_datepulish;
        this.chapter_idcomic=chapter_idcomic;
        this.chapter_img=chapter_img;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row_chapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.chapter_id_txt.setText(String.valueOf(chapter_id.get(position)));
        holder.chapter_name_txt.setText(String.valueOf(chapter_name.get(position)));
        holder.chapter_viewer_txt.setText(String.valueOf(chapter_viewer.get(position)));
        holder.chapter_datepublish_txt.setText(String.valueOf(chapter_datepulish.get(position)));
        List<byte[]> chapterImages = chapter_img.get(position);
        holder.chapter_idcomic_txt.setText(String.valueOf(chapter_idcomic.get(position)));

        // Hiển thị hình ảnh trong các ImageView
        for (int i = 0; i < Math.min(chapterImages.size(), holder.chapter_img_imgview.size()); i++) {
            byte[] imgBytes = chapterImages.get(i);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            holder.chapter_img_imgview.get(i).setImageBitmap(bitmap);
        }
        holder.mainLayout2.setOnClickListener(v -> {
            Intent intent= new Intent((context), UpdateChapterActivity.class);
            intent.putExtra("id",String.valueOf(chapter_id.get(position)));
            intent.putExtra("chapter",String.valueOf(chapter_name.get(position)));
            intent.putExtra("viewer",String.valueOf(chapter_viewer.get(position)));
            intent.putExtra("datepublish",String.valueOf(chapter_datepulish.get(position)));
            // Chuyển danh sách hình ảnh qua UpdateChapterActivity
            Bundle bundle = new Bundle();
            bundle.putSerializable("img", (Serializable) chapter_img.get(position));
            intent.putExtras(bundle);
            intent.putExtra("id_comic",String.valueOf(chapter_idcomic.get(position)));
            activity.startActivityForResult(intent,1);
        });
    }

    @Override
    public int getItemCount() {
        return chapter_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout2;
        List<ImageView> chapter_img_imgview;
        TextView chapter_id_txt,chapter_name_txt, chapter_viewer_txt,chapter_datepublish_txt,chapter_idcomic_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter_id_txt=itemView.findViewById(R.id. chapter_id_txt);
            chapter_name_txt=itemView.findViewById(R.id.chapter_name_txt);
            chapter_viewer_txt=itemView.findViewById(R.id.chapter_viewer_txt);
            chapter_datepublish_txt=itemView.findViewById(R.id.chapter_datepublish_txt);
            chapter_idcomic_txt=itemView.findViewById(R.id.chapter_idcomic_txt);
            chapter_img_imgview = new ArrayList<>();
            chapter_img_imgview.add(itemView.findViewById(R.id.chapter_img1_img));
            chapter_img_imgview.add(itemView.findViewById(R.id.chapter_img2_img));
            chapter_img_imgview.add(itemView.findViewById(R.id.chapter_img3_img));
            chapter_img_imgview.add(itemView.findViewById(R.id.chapter_img4_img));
            chapter_img_imgview.add(itemView.findViewById(R.id.chapter_img5_img));
            mainLayout2=itemView.findViewById(R.id.mainLayout2);
        }
    }
}
