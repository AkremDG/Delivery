package com.example.deliveryboy.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.R;

public class ClientsVh extends RecyclerView.ViewHolder {

     TextView clientName_tv;
             TextView clientStatus_tv;
             TextView clientRegion_tv;


    public ClientsVh( View itemView) {
        super(itemView);

       clientName_tv = itemView.findViewById(R.id.clientName_tv);
      clientStatus_tv = itemView.findViewById(R.id.statusClient_tv);
   clientRegion_tv = itemView.findViewById(R.id.regionName_tv);

    }
}
