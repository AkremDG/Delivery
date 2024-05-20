package com.example.deliveryboy.Adapters.DemandesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Adapters.ClientsVh;
import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;
import com.example.deliveryboy.R;

import java.util.List;

public class DemandesRvAdapter extends RecyclerView.Adapter<DemandesVh> {

    private Context context;
    private List<GETDemandeChargementRes> demandesList;



    public DemandesRvAdapter(Context context, List<GETDemandeChargementRes> demandeChargementResList) {
        this.context = context;
        this.demandesList = demandeChargementResList;
    }

    @NonNull
    @Override
    public DemandesVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemandesVh(LayoutInflater.from(context).inflate(R.layout.demande_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DemandesVh holder, int position) {

        holder.demandeStatus.setText(this.demandesList.get(position).getStatut());
        holder.demandeIdTv.setText("Demande : "+this.demandesList.get(position).getBoId());
        holder.demandeDate.setText(this.demandesList.get(position).getDO_Date());

        String date = this.demandesList.get(position).getDO_Date();
        if (date != null && date.length() > 10) {
            String modifiedDate = date.substring(0, 10) + " à " + date.substring(10);
            holder.demandeDate.setText(modifiedDate);
        } else {
            holder.demandeDate.setText(date); // Handle the case where the date is null or too short
        }


    }

    @Override
    public int getItemCount() {
        return this.demandesList.size();
    }
}
