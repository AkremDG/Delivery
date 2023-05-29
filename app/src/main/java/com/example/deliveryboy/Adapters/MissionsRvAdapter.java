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
    private final RvInterface rvInterface;


    public static class CommandesVh extends RecyclerView.ViewHolder {
        TextView Uuser_name_visite_Tv,type_visite_Tv,zone_Tv;

        public CommandesVh(@NonNull View itemView, RvInterface rvInterface) {
            super(itemView);

            Uuser_name_visite_Tv=itemView.findViewById(R.id.Uuser_name_visite_Tv);
            type_visite_Tv=itemView.findViewById(R.id.type_visite_Tv);
            zone_Tv=itemView.findViewById(R.id.zone_Tv);

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


    public MissionsRvAdapter(Context context, List<Visite> visiteList,RvInterface rvInterface ) {
        this.context = context;
        this.visiteList = visiteList;
        this.rvInterface=rvInterface;
    }

    @NonNull
    @Override
    public CommandesVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.cmd_rv_item,parent,false);

        return new MissionsRvAdapter.CommandesVh(view,rvInterface);
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
