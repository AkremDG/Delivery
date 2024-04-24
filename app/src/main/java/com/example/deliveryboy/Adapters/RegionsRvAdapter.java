package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.R;

import java.util.List;

public class RegionsRvAdapter extends RecyclerView.Adapter<RegionsVh>{

    Context context;
    List<String> regionsList;

    public RegionsRvAdapter(Context context, List<String> regionsList) {
        this.context=context;
        this.regionsList=regionsList;
    }

    @NonNull
    @Override
    public RegionsVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RegionsVh(LayoutInflater.from(this.context).inflate(R.layout.regions_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegionsVh holder, int position) {
        holder.regionName_tv.setText(regionsList.get(position));

    }

    @Override
    public int getItemCount() {
        return this.regionsList.size();
    }
}
