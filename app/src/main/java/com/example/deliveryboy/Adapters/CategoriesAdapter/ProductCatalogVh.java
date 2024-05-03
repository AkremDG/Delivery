package com.example.deliveryboy.Adapters.CategoriesAdapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.R;

public class ProductCatalogVh extends RecyclerView.ViewHolder  {

    TextView catalog_Tv;
    ConstraintLayout constraintLayout;

    ImageView addCatalogIv;


    public ProductCatalogVh(View itemView) {
        super(itemView);

        catalog_Tv = itemView.findViewById(R.id.catalog_Tv);
        constraintLayout = itemView.findViewById(R.id.constraintLayoutCatalog);
        addCatalogIv = itemView.findViewById(R.id.addCatalogIv);
    }


}
