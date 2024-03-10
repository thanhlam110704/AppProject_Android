package com.example.appproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.R;
import com.example.appproject.admin.UpdateComicGenreActivity;

import java.util.ArrayList;

public class CustomComicGenAdapter extends RecyclerView.Adapter<CustomComicGenAdapter.MyViewHolder>  {
    Activity activity;
    private Context context;
    private ArrayList comicgenre_id ,comicgenre_idgen,comicgenre_idcomic;
    public CustomComicGenAdapter(Activity activity, Context context,
                                 ArrayList comicgenre_id, ArrayList comicgenre_idcomic, ArrayList comicgenre_idgen){
        this.activity=activity;
        this.context=context;
        this.comicgenre_id =comicgenre_id ;
        this.comicgenre_idcomic=comicgenre_idcomic;
        this.comicgenre_idgen=comicgenre_idgen;
    }


    @NonNull
    @Override
    public CustomComicGenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row_comicgen,parent,false);
        return new CustomComicGenAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomComicGenAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.comicgenre_id_txt.setText(String.valueOf(comicgenre_id.get(position)));
        holder.comicgenre_idcomic_txt.setText(String.valueOf(comicgenre_idcomic.get(position)));
        holder.comicgenre_idgen_txt.setText(String.valueOf(comicgenre_idgen.get(position)));
        holder.mainLayout4.setOnClickListener(v -> {
            Intent intent= new Intent((context), UpdateComicGenreActivity.class);
            intent.putExtra("id",String.valueOf(comicgenre_id.get(position)));
            intent.putExtra("idcomic",String.valueOf(comicgenre_idcomic.get(position)));
            intent.putExtra("idgen",String.valueOf(comicgenre_idgen.get(position)));
            activity.startActivityForResult(intent,1);
        });
    }


    @Override
    public int getItemCount() {
        return comicgenre_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout4;
        TextView comicgenre_id_txt,comicgenre_idcomic_txt,comicgenre_idgen_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comicgenre_id_txt=itemView.findViewById(R.id. comicgenre_id_txt);
            comicgenre_idcomic_txt=itemView.findViewById(R.id.comicgenre_idcomic_txt);
            comicgenre_idgen_txt=itemView.findViewById(R.id.comicgenre_idgen_txt);
            mainLayout4=itemView.findViewById(R.id.mainLayout4);
        }
    }
}
