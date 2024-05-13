package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Model.CustomProduit;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.Responses.LocalPriceAndQuantity;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.DemandeFragments.CreateDemande;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProduitRvAdapter extends RecyclerView.Adapter<ProduitRvAdapter.ProduitVh> {
    Context context;
    List<Produit> produitList;
    private final RvInterface rvInterface;
    private final quantiteInterface quantiteInterface;

    DemandeChargViewModel demandeChargViewModel;
    LifecycleOwner lifecycleOwner;
    public ProduitRvAdapter(LifecycleOwner lifecycleOwner, Context context, List<Produit> produitList, RvInterface rvInterface, quantiteInterface quantiteInterface) {
        this.context = context;
        this.produitList = produitList;
        this.rvInterface = rvInterface;
        this.quantiteInterface=quantiteInterface;

        this.lifecycleOwner = lifecycleOwner;
        demandeChargViewModel = new DemandeChargViewModel();
    }

    @NonNull
    @Override
    public ProduitVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.produit_rv_item,parent,false);

        return new ProduitRvAdapter.ProduitVh(view,this.rvInterface, this.quantiteInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitVh holder, int position) {
        int recyclerPosition = position;
        holder.nomProduit_Tv.setText(produitList.get(position).getAR_Design());
        List<ProduitCondition> produitConditionList = new ArrayList<>();

        List<String> stringList = new ArrayList<>();


        holder.progressBar.setVisibility(View.VISIBLE);

        demandeChargViewModel.getLocalProductsConditions(context, produitList.get(position).getBoId()).observe(lifecycleOwner,
                new Observer<List<ProduitCondition>>() {
            @Override
            public void onChanged(List<ProduitCondition> produitConditions) {
                produitConditionList.addAll(produitConditions);

                holder.prix_Tv.setText(String.valueOf(produitConditions.get(0).getTC_Prix())+" dt");
                holder.ArRef_Tv.setText(String.valueOf(" Stock : "+produitConditions.get(0).getAS_QteSto()));

                for(ProduitCondition produitCondition : produitConditions){

                    if(!stringList.contains(produitCondition.getEC_Enumere())){
                        stringList.add(produitCondition.getEC_Enumere());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.customspinner,R.id.regionName_tv, stringList);
                    holder.conditionSpinner.setAdapter(adapter);
                }

                holder.produit_Iv.setImageResource(R.drawable.bag);
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
        });

        holder.conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                demandeChargViewModel.getLocalPriceByIdAndProductId(context,produitList.get(recyclerPosition).getBoId(),
                        String.valueOf(parent.getItemAtPosition(position))).observe(lifecycleOwner, new Observer<ProduitCondition>() {
                    @Override
                    public void onChanged(ProduitCondition produitCondition) {

                        if(produitCondition!=null){
                            holder.prix_Tv.setText(String.valueOf(produitCondition.getTC_Prix())+" dt");
                            holder.ArRef_Tv.setText("Stock : "+String.valueOf(produitCondition.getAS_QteSto()));

                            if(produitCondition.getAS_QteSto()==0){
                                holder.ArRef_Tv.setTextColor(Color.RED);
                            }else {
                                holder.ArRef_Tv.setTextColor(Color.parseColor("#49968C"));
                            }
                        }

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return produitList.size();
    }

    public static class ProduitVh extends RecyclerView.ViewHolder {
        Context context;
        ImageView produit_Iv,moins_Iv,plus_Iv;
        Spinner conditionSpinner;
        TextView pack_Tv,qte_surSomme_Tv,nomProduit_Tv,ArRef_Tv,prix_Tv,qte_Tv;
        ProgressBar progressBar;
        int i=1 ;
        int pos;

        public ProduitVh(@NonNull View itemView, RvInterface rvInterface, quantiteInterface quantiteInterface) {
            super(itemView);
// Find the ProgressBar in your RecyclerView item
             progressBar = itemView.findViewById(R.id.progressBar);


            produit_Iv=itemView.findViewById(R.id.produit_Iv);
            moins_Iv=itemView.findViewById(R.id.moins_Iv);
            conditionSpinner = itemView.findViewById(R.id.conditionSpinner);
            plus_Iv=itemView.findViewById(R.id.plus_Iv);
            pack_Tv=itemView.findViewById(R.id.pack_Tv);
            qte_surSomme_Tv=itemView.findViewById(R.id.qte_surSomme_Tv);
            nomProduit_Tv=itemView.findViewById(R.id.nomProduit_Tv);
            ArRef_Tv=itemView.findViewById(R.id.arRef_tv);
            prix_Tv=itemView.findViewById(R.id.prix_Tv);
            qte_Tv=itemView.findViewById(R.id.qte_Tv);

            this.context=context;





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedConditionement = (String) conditionSpinner.getSelectedItem();
                    if(rvInterface != null){
                         pos= getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            rvInterface.onItemClickReturnObject(new CustomProduit(
                                    nomProduit_Tv.getText().toString(),
                                    prix_Tv.getText().toString(),
                                    ArRef_Tv.getText().toString(),
                                    qte_Tv.getText().toString(),
                                    selectedConditionement
                            ),pos);
                        }
                    }
                }
            });




            plus_Iv.setOnClickListener(new View.OnClickListener() {;
                @Override
                public void onClick(View v) {
                    i++;
                    qte_Tv.setText(String.valueOf(i));


                    quantiteInterface.onValidQte(i);

                }
            });
            quantiteInterface.onValidQte(i);

            moins_Iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i--;
                    qte_Tv.setText(String.valueOf(i));
                    quantiteInterface.onValidQte(i);
                    if(i<1){
                        i=0;
                        qte_Tv.setText("0");
                    }
                }
            });


        }
    }

}
