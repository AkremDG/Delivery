package com.example.deliveryboy.Adapters;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.R;

public class RegionsVh extends RecyclerView.ViewHolder  {

    TextView regionName_tv;

    public RegionsVh(View itemView) {
        super(itemView);

        regionName_tv = itemView.findViewById(R.id.regionName_tv);
    }


}
