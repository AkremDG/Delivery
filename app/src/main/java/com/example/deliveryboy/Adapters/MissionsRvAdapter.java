package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;

import java.util.List;

public class MissionsRvAdapter extends RecyclerView.Adapter<MissionsRvAdapter.CommandesVh> {
    Context context;
    List<Mission> visiteList;
    private final RvInterface rvInterface;


    public static class CommandesVh extends RecyclerView.ViewHolder {
        TextView Uuser_name_visite_Tv,type_visite_Tv,startsDate_tv,endDate_tv,nbClients_tv;

        public CommandesVh(@NonNull View itemView, RvInterface rvInterface) {
            super(itemView);
            startsDate_tv = itemView.findViewById(R.id.startsDate_tv);
            endDate_tv = itemView.findViewById(R.id.endDate_tv);
            nbClients_tv = itemView.findViewById(R.id.nbClients_tv);
            Uuser_name_visite_Tv=itemView.findViewById(R.id.Uuser_name_visite_Tv);
            type_visite_Tv=itemView.findViewById(R.id.type_visite_Tv);

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


    public MissionsRvAdapter(Context context, List<Mission> visiteList,RvInterface rvInterface ) {
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
        holder.Uuser_name_visite_Tv.setText("Mission : "+String.valueOf(this.visiteList.get(position).getMissionId()));

        holder.startsDate_tv.setText(this.visiteList.get(position).getStartOn()+" - ");
        holder.endDate_tv.setText(this.visiteList.get(position).getEndsOn());
//      holder.nbClients_tv.setText(String.valueOf(this.visiteList.get(position).getClientsList().size())+" Clients");


    }

    @Override
    public int getItemCount() {
        return visiteList.size();
    }
}
