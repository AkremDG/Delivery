package com.example.deliveryboy.Adapters.DemandesAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.R;

public class DemandesVh extends RecyclerView.ViewHolder {
    TextView demandeIdTv, demandeDate, demandeStatus;

    public DemandesVh(@NonNull View itemView) {
        super(itemView);

        demandeIdTv = itemView.findViewById(R.id.idDemande_tv);
        demandeDate = itemView.findViewById(R.id.startsDate_tv);
        demandeStatus = itemView.findViewById(R.id.status_tv);

    }


}
