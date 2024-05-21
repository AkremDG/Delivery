package com.example.deliveryboy.Adapters.DemandesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Adapters.ClientsVh;
import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;
import com.example.deliveryboy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DemandesRvAdapter extends RecyclerView.Adapter<DemandesVh> {

    private Context context;
    private List<GETDemandeChargementRes> demandesList;

    private MutableLiveData<GETDemandeChargementRes> clickedChargementResMutableLiveData;



    public DemandesRvAdapter(Context context, List<GETDemandeChargementRes> demandeChargementResList) {
        this.context = context;
        this.demandesList = demandeChargementResList;
        clickedChargementResMutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DemandesVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemandesVh(LayoutInflater.from(context).inflate(R.layout.demande_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DemandesVh holder, int position) {

        if(this.demandesList.get(position).getStatut().equals("En Cours")){
            holder.demandeStatus.setTextColor(context.getResources().getColor(R.color.blue_reset_password));
        }
        if(this.demandesList.get(position).getStatut().equals("Cloturée")){
            holder.demandeStatus.setTextColor(context.getResources().getColor(R.color.orange_btn_color));
        }
        if(this.demandesList.get(position).getStatut().equals("Archivée")){
            holder.demandeStatus.setTextColor(context.getResources().getColor(R.color.input_text_color));
        }
        holder.demandeStatus.setText(this.demandesList.get(position).getStatut());

        holder.demandeIdTv.setText("Demande : "+this.demandesList.get(position).getBoId());

        holder.demandeDate.setText(this.demandesList.get(position).getDO_Date()+ " à " +this.demandesList.get(position).getDo_Time());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    clickedChargementResMutableLiveData.postValue(demandesList.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.demandesList.size();
    }

    public MutableLiveData<GETDemandeChargementRes> getClickedChargementResMutableLiveData() {
        return clickedChargementResMutableLiveData;
    }
}
