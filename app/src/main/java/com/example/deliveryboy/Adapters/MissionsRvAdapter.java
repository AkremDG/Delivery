package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Model.Commande;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;

import java.util.List;

public class MissionsRvAdapter extends RecyclerView.Adapter<MissionsRvAdapter.CommandesVh> {
    Context context;
    List<Visite> visiteList;


    public static class CommandesVh extends RecyclerView.ViewHolder {
        TextView Uuser_name_visite_Tv,type_visite_Tv,zone_Tv;

        public CommandesVh(@NonNull View itemView) {
            super(itemView);

            Uuser_name_visite_Tv=itemView.findViewById(R.id.Uuser_name_visite_Tv);
            type_visite_Tv=itemView.findViewById(R.id.type_visite_Tv);
            zone_Tv=itemView.findViewById(R.id.zone_Tv);

        }
    }


    public MissionsRvAdapter(Context context, List<Visite> visiteList ) {
        this.context = context;
        this.visiteList = visiteList;
    }

    @NonNull
    @Override
    public CommandesVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommandesVh(LayoutInflater.from(this.context).
                inflate(R.layout.cmd_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommandesVh holder, int position) {

        holder.Uuser_name_visite_Tv.setText(visiteList.get(position).getUser().getNameUser());
        holder.type_visite_Tv.setText(visiteList.get(position).getTypeVisite());

        holder.zone_Tv.setText(String.valueOf(visiteList.get(position).getZone()));

    }

    @Override
    public int getItemCount() {
        return visiteList.size();
    }
}
