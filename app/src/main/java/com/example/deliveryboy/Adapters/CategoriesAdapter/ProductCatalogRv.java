package com.example.deliveryboy.Adapters.CategoriesAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Adapters.RegionClick;
import com.example.deliveryboy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductCatalogRv extends RecyclerView.Adapter<ProductCatalogVh>{

    private List<String> catalogsList;
    private boolean[] isSelectedArray;

    Context context;
    CatalogClick catalogClick ;

    RecyclerView recyclerView;


    public ProductCatalogRv(Context context, List<String> catalogsList, CatalogClick catalogClick, RecyclerView recyclerView) {
        this.context=context;
        this.catalogsList=catalogsList;
        this.catalogClick = catalogClick;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ProductCatalogVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductCatalogVh(LayoutInflater.from(this.context).inflate(R.layout.catalog_item,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ProductCatalogVh holder, @SuppressLint("RecyclerView") int position) {
        holder.catalog_Tv.setText(catalogsList.get(position));

        // Check if isSelectedArray is initialized and has the correct size
        if (isSelectedArray == null || isSelectedArray.length != catalogsList.size()) {
            isSelectedArray = new boolean[catalogsList.size()];
            Arrays.fill(isSelectedArray, false);
        }

        // Set the UI state based on the selection state
        if (isSelectedArray[position]) {
            holder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_white);
            holder.catalog_Tv.setTextColor(Color.parseColor("#FFFFFF"));
            holder.addCatalogIv.setImageResource(R.drawable.baseline_check_24);
        } else {
            holder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_orange);
            holder.catalog_Tv.setTextColor(Color.parseColor("#F8981D"));
            holder.addCatalogIv.setImageResource(R.drawable.baseline_add_circle_24);
        }

        // Set click listener
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update UI of the previously clicked item
                for (int i = 0; i < isSelectedArray.length; i++) {
                    if (isSelectedArray[i] && i != position) {
                        isSelectedArray[i] = false;
                        ProductCatalogVh previousViewHolder = (ProductCatalogVh) recyclerView.findViewHolderForAdapterPosition(i);
                        if (previousViewHolder != null) {
                            previousViewHolder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_orange);
                            previousViewHolder.catalog_Tv.setTextColor(Color.parseColor("#F8981D"));
                            previousViewHolder.addCatalogIv.setImageResource(R.drawable.baseline_add_circle_24);
                        }
                    }
                }

                // Toggle the selection state of the clicked item
                isSelectedArray[position] = !isSelectedArray[position];

                // Update UI for the clicked item based on its selection state
                if (isSelectedArray[position]) {
                    holder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_white);
                    holder.catalog_Tv.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.addCatalogIv.setImageResource(R.drawable.baseline_check_24);
                } else {
                    holder.constraintLayout.setBackgroundResource(R.drawable.search_view_stroke_orange);
                    holder.catalog_Tv.setTextColor(Color.parseColor("#F8981D"));
                    holder.addCatalogIv.setImageResource(R.drawable.baseline_add_circle_24);
                }

                // Create a list to hold selected items
                List<String> selectedItems = new ArrayList<>();
                for (int i = 0; i < isSelectedArray.length; i++) {
                    if (isSelectedArray[i]) {
                        selectedItems.add(catalogsList.get(i));
                    }
                }

                // Pass the list of selected items to the callback method
                catalogClick.onCatalogClick(selectedItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.catalogsList.size();
    }
}