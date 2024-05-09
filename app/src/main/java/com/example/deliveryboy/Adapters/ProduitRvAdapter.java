package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.R;
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
        holder.nomProduit_Tv.setText(produitList.get(position).getAR_Design());

       // holder.produit_Iv.setImageResource(produitList.get(position).getImageProduit());
        holder.ArRef_Tv.setText("Ref : "+produitList.get(position).getAR_Ref());
       // holder.qte_Tv.setText(String.valueOf(produitList.get(position).getQuantiteProduit()));

        List<String> stringList = new ArrayList<>();

        demandeChargViewModel.getLocalProductsConditions(context, produitList.get(position).getBoId()).
                observe(lifecycleOwner, new Observer<List<ProduitCondition>>() {
            @Override
            public void onChanged(List<ProduitCondition> produitConditions) {

                holder.prix_Tv.setText(String.valueOf(produitConditions.get(0).getTC_Prix())+" dt");


                for(ProduitCondition produitCondition : produitConditions){





                    if(!stringList.contains(produitCondition.getEC_Enumere())){
                        stringList.add(produitCondition.getEC_Enumere());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.customspinner,R.id.regionName_tv, stringList);
                    holder.conditionSpinner.setAdapter(adapter);
                }

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
        int i=1 ;
        int pos;

        public ProduitVh(@NonNull View itemView, RvInterface rvInterface, quantiteInterface quantiteInterface) {
            super(itemView);
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
                    if(rvInterface != null){
                         pos= getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            rvInterface.onItemClick(pos);
                        }
                    }
                }
            });



            /*
            List<String> stringList = new ArrayList<>();

            stringList.add("Présentoir");
            stringList.add("Pack");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(itemView.getContext(), R.layout.customspinner,R.id.regionName_tv, stringList);
            conditionSpinner.setAdapter(adapter);

             */



            plus_Iv.setOnClickListener(new View.OnClickListener() {
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
