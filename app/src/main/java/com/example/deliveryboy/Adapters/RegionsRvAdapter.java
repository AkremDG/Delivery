package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionsRvAdapter extends RecyclerView.Adapter<RegionsVh>{

    private List<String> regionsList;
    private boolean[] isSelectedArray;

    Context context;
    RegionClick regionClick ;

    RecyclerView recyclerView;


    public RegionsRvAdapter(Context context, List<String> regionsList, RegionClick regionClick,RecyclerView recyclerView) {
        this.context=context;
        this.regionsList=regionsList;
        this.regionClick = regionClick;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RegionsVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RegionsVh(LayoutInflater.from(this.context).inflate(R.layout.regions_item,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull RegionsVh holder, int position) {
        holder.regionName_tv.setText(regionsList.get(position));

        // Check if isSelectedArray is initialized and has the correct size
        if (isSelectedArray == null || isSelectedArray.length != regionsList.size()) {
            isSelectedArray = new boolean[regionsList.size()];
            Arrays.fill(isSelectedArray, false);
        }

        // Set the UI state based on the selection state
        if (isSelectedArray[position]) {
            holder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_white);
            holder.regionName_tv.setTextColor(Color.parseColor("#FFFFFF"));
            holder.plusIcon.setImageResource(R.drawable.baseline_check_24);
        } else {
            holder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_orange);
            holder.regionName_tv.setTextColor(Color.parseColor("#F8981D"));
            holder.plusIcon.setImageResource(R.drawable.baseline_add_circle_24);
        }

        // Set click listener
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update UI of the previously clicked item
                for (int i = 0; i < isSelectedArray.length; i++) {
                    if (isSelectedArray[i] && i != position) {
                        isSelectedArray[i] = false;
                        RegionsVh previousViewHolder = (RegionsVh) recyclerView.findViewHolderForAdapterPosition(i);
                        if (previousViewHolder != null) {
                            previousViewHolder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_orange);
                            previousViewHolder.regionName_tv.setTextColor(Color.parseColor("#F8981D"));
                            previousViewHolder.plusIcon.setImageResource(R.drawable.baseline_add_circle_24);
                        }
                    }
                }

                // Toggle the selection state of the clicked item
                isSelectedArray[position] = !isSelectedArray[position];

                // Update UI for the clicked item based on its selection state
                if (isSelectedArray[position]) {
                    holder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_white);
                    holder.regionName_tv.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.plusIcon.setImageResource(R.drawable.baseline_check_24);
                } else {
                    holder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_orange);
                    holder.regionName_tv.setTextColor(Color.parseColor("#F8981D"));
                    holder.plusIcon.setImageResource(R.drawable.baseline_add_circle_24);
                }

                // Create a list to hold selected items
                List<String> selectedItems = new ArrayList<>();
                for (int i = 0; i < isSelectedArray.length; i++) {
                    if (isSelectedArray[i]) {
                        selectedItems.add(regionsList.get(i));
                    }
                }

                // Pass the list of selected items to the callback method
                regionClick.onRegionClick(selectedItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.regionsList.size();
    }
}