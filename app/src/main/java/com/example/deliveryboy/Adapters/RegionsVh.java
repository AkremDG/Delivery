package com.example.deliveryboy.Adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.R;

public class RegionsVh extends RecyclerView.ViewHolder  {

    TextView regionName_tv;
    ConstraintLayout constraintLayout;

    ImageView plusIcon;


    public RegionsVh(View itemView) {
        super(itemView);

        regionName_tv = itemView.findViewById(R.id.regionName_tv);
        constraintLayout = itemView.findViewById(R.id.constraintLayout);
        plusIcon = itemView.findViewById(R.id.plusIv);
    }


}
