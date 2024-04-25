package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Model.Client;
import com.example.deliveryboy.R;

import java.util.ArrayList;
import java.util.List;

public class ClientsRvAdapter extends RecyclerView.Adapter<ClientsVh> {

     Context context;
     List<Client> clientList ;
    public ClientsRvAdapter(Context context,List<Client> clients) {
        this.context = context;
        this.clientList = clients;

    }

    @NonNull
    @Override
    public ClientsVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClientsVh(LayoutInflater.from(context).inflate(R.layout.clients_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsVh holder, int position) {
        holder.clientName_tv.setText(clientList.get(position).getCT_Intitule());
        holder.clientStatus_tv.setText(clientList.get(position).getStatutC());
        holder.clientRegion_tv.setText(clientList.get(position).getRegion().getRegionName());

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }
}
