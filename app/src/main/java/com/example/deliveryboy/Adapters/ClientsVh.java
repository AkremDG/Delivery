package com.example.deliveryboy.Adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.R;

public class ClientsVh extends RecyclerView.ViewHolder {

     TextView clientName_tv;
     TextView clientStatus_tv;
     TextView clientRegion_tv;

    ImageView commanderIv;

    public ClientsVh( View itemView, RvInterface rvInterface) {
        super(itemView);

       clientName_tv = itemView.findViewById(R.id.clientName_tv);
      clientStatus_tv = itemView.findViewById(R.id.statusClient_tv);
      clientRegion_tv = itemView.findViewById(R.id.regionName_tv);
        commanderIv = itemView.findViewById(R.id.commanderIv);

        commanderIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvInterface != null){
                    int pos= getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        rvInterface.onCommanderClick(pos);
                    }
                }
            }
        });




        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rvInterface != null){
                    int pos= getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        rvInterface.onItemClick(pos);
                    }
                }
            }
        });

    }
}
