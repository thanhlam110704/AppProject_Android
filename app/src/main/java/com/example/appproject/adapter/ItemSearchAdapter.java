package com.example.appproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appproject.R;
import com.example.appproject.model.ItemSearch;

import java.util.ArrayList;
import java.util.List;

public class ItemSearchAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<ItemSearch> originalArrayList; // Original data
    private List<ItemSearch> filteredArrayList; // Filtered data for search

    public ItemSearchAdapter(Context context, int layout, List<ItemSearch> arrayList) {
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

        convertView = inflater.inflate(layout, null);
        ItemSearch itemSearch = filteredArrayList.get(position);
        TextView textV1 = convertView.findViewById(R.id.txtTenTruyen_search);
        TextView textV2 = convertView.findViewById(R.id.txtTenChap_search);
        TextView textV3 = convertView.findViewById(R.id.txtTheLoai_search);
        ImageView imageView = convertView.findViewById(R.id.imgTruyen_search);
        textV1.setText(itemSearch.getTenTruyen());
        textV2.setText(itemSearch.getTenChap());
        textV3.setText(itemSearch.getTheLoai());
        imageView.setImageResource(itemSearch.getLinkAnh());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ItemSearch> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // No filter applied, show all items
                    filteredList.addAll(originalArrayList);
                } else {
                    // Filter items based on the search query
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ItemSearch item : originalArrayList) {
                        if (item.getTenTruyen().toLowerCase().contains(filterPattern)
                                || item.getTenChap().toLowerCase().contains(filterPattern)
                                || item.getTheLoai().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
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
                filteredArrayList.addAll((List<ItemSearch>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
