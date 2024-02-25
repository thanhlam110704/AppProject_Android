package com.example.appproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appproject.R;
import com.example.appproject.object.TruyenTranh;

import java.util.List;

public class TruyenTranhDeCuAdapter extends RecyclerView.Adapter<TruyenTranhDeCuAdapter.BookViewHolder> {
    private List<TruyenTranh> mComics;

    public void setData(List<TruyenTranh> list) {
        this.mComics = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_truyendecu, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        TruyenTranh truyen = mComics.get(position);
        if (truyen == null) {
            return;
        }

        // Use Glide to load the image from the web URL
        Glide.with(holder.itemView.getContext())
                .load(truyen.getLinkAnh())
                .into(holder.imgComic);

        holder.tenTruyen.setText(truyen.getTenTruyen());
        holder.tenChap.setText(truyen.getTenChap());
    }

    @Override
    public int getItemCount() {
        if (mComics != null) {
            return mComics.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgComic;
        private TextView tenTruyen;
        private TextView tenChap;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgComic = itemView.findViewById(R.id.img_book);
            tenTruyen = itemView.findViewById(R.id.txtTenTruyen);
            tenChap = itemView.findViewById(R.id.txtTenChap);
        }
    }
}
