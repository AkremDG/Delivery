package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.SelectedProduit;
import com.example.deliveryboy.R;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PanierRvAdapter extends RecyclerView.Adapter<PanierRvAdapter.PanierVH>{
    Double newTotalPanier = 0.0;

    ProduitCondition newSelectedCondition;
    Double totalPanier = 0.0;
    String selectedCondition;
    Context context;
    List<SelectedProduit> listProduit;
    List<SelectedProduit> modifiedListProduit;

    quantiteInterface quantiteInterface;
    int quantite;
    LifecycleOwner lifecycleOwner;

    DemandeChargViewModel demandeChargViewModel;
    PanierCallbacks panierCallbacks;

    MutableLiveData<List<SelectedProduit>> modifiedListProduitLiveData;

    public PanierRvAdapter(LifecycleOwner lifecycleOwner, Context context, List<SelectedProduit> listProduit,
                           quantiteInterface quantiteInterface, PanierCallbacks panierCallbacks) {
        this.context = context;
        this.listProduit = listProduit;
        this.quantiteInterface=quantiteInterface;
        this.lifecycleOwner = lifecycleOwner;
        this.panierCallbacks = panierCallbacks;
        demandeChargViewModel = new DemandeChargViewModel();
        modifiedListProduitLiveData = new MutableLiveData<>();
        modifiedListProduit = listProduit;

    }

    @NonNull
    @Override
    public PanierVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.panier_rv_item,parent,false);

        return new PanierRvAdapter.PanierVH(view, this.quantiteInterface);
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public void onBindViewHolder(@NonNull PanierVH holder, int position) {

        int rvPosition = position;


        /////////// CONDITIONNEMENT
        List<String> itemConditions = new ArrayList<>();
        itemConditions.add(listProduit.get(position).getSelectedCondition());

        for(String item : listProduit.get(position).getArticlesConditionsStrings()){
            if(!itemConditions.contains(item)){
                itemConditions.add(item);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.customspinner,R.id.regionName_tv,
                itemConditions);
        holder.conditionSpinner.setAdapter(adapter);

        holder.conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedCondition = (String) parent.getItemAtPosition(position).toString();


                demandeChargViewModel.getLocalPriceByIdAndProductId(context, listProduit.get(rvPosition).getBoId(),

                                selectedCondition).
                        observe(lifecycleOwner, new Observer<ProduitCondition>() {
                    @Override
                    public void onChanged(ProduitCondition produitCondition) {

                         newSelectedCondition = produitCondition;

                        if(produitCondition!=null){


                           holder.unitPriceValTv.setText(String.valueOf(produitCondition.getTC_Prix()));
                           holder.stockValTv.setText(String.valueOf(produitCondition.getAS_QteSto()));

                            Double totalProductPrice = Integer.valueOf(holder.qte_Tv.getText().toString()) * Double.valueOf(newSelectedCondition.getTC_Prix());
                            holder.totalVal_tv.setText(String.valueOf(String.valueOf(totalProductPrice)));

                            holder.totalVal_tv.setText(String.valueOf(totalProductPrice));


                            if(produitCondition.getAS_QteSto()==0){
                                holder.stockValTv.setTextColor(Color.RED);
                            }else {
                                holder.stockValTv.setTextColor(Color.parseColor("#49968C"));
                            }
                        }

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///////////////

        holder.unitPriceValTv.setText(String.valueOf(listProduit.get(position).getSelectedProductPrice()));

        holder.panier_produit_Iv.setImageResource(R.drawable.bag);

        holder.nomProdPanier_Tv.setText(listProduit.get(position).getAR_Design());

        holder.stockValTv.setText(String.valueOf(listProduit.get(position).getSelectedProductStock()));

        holder.totalVal_tv.setText(String.valueOf(listProduit.get(position).getSelectedProductTotalPrice())+ " dt");

        holder.qte_Tv.setText(String.valueOf(listProduit.get(position).getSelectedProductQuantity()));


        int itemSelectedQuantitty =1 ;

        holder.qte_Tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {

                    int selectedQuantity = Integer.valueOf(s.toString());

                   String strStock = holder.stockValTv.getText().toString();

                    int formattedActualStock = (int) Math.floor(Double.valueOf(strStock));



                    if(selectedQuantity>formattedActualStock){
                        holder.qte_Tv.setText(String.valueOf(holder.stockValTv.getText().toString()));
                        holder.qte_Tv.setTextColor(context.getResources().getColor(R.color.orange_btn_color));



                        holder.qte_Tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                holder.qte_Tv.setText("");

                            }
                        });


                    }else if (selectedQuantity<=0){
                        holder.qte_Tv.setText(String.valueOf(1));

                    }
                    else {
                        holder.qte_Tv.setTextColor(Color.parseColor("#FFA5A5A5"));

                    }

                    Double totalProductPrice = Integer.valueOf(holder.qte_Tv.getText().toString()) * Double.valueOf(newSelectedCondition.getTC_Prix());


                    holder.totalVal_tv.setText(String.valueOf(String.valueOf(totalProductPrice)));


                    //CALCULATION TOTAL DEMANDE
                    listProduit.get(rvPosition).setSelectedProductTotalPrice(totalProductPrice);
                    for(SelectedProduit selectedProduit : listProduit){
                        totalPanier = totalPanier + selectedProduit.getSelectedProductTotalPrice();
                    }
                    panierCallbacks.totalPanierCallback(totalPanier);

                    ///////////////////////////

                }catch (Exception e){


                }




            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.equals("")) {
                    modifiedListProduit.get(rvPosition).setSelectedProductPrice(Double.valueOf(holder.unitPriceValTv.getText().toString()));

                    try {
                        modifiedListProduit.get(rvPosition).setSelectedProductQuantity(Integer.valueOf(s.toString()));

                    }catch (Exception e){

                    }


                    modifiedListProduit.get(rvPosition).setIdArtConditionnement(newSelectedCondition.getIdart());

                    try {
                        modifiedListProduit.get(rvPosition).
                                setSelectedProductTotalPrice(
                                        Integer.valueOf(holder.qte_Tv.getText().toString()) * Double.valueOf(holder.unitPriceValTv.getText().toString())
                                );
                    }catch (Exception e) {

                    }



                    modifiedListProduitLiveData.postValue(modifiedListProduit);
                }


            }
        });


        holder.unitPriceValTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {




                modifiedListProduit.get(rvPosition).setSelectedProductPrice(Double.valueOf(s.toString()));
                modifiedListProduit.get(rvPosition).setSelectedProductQuantity(Integer.valueOf(holder.qte_Tv.getText().toString()));


                /*

                demandeChargViewModel.getLocalPriceByIdAndProductId(context, listProduit.get(rvPosition).getBoId(), holder.conditionSpinner.getSelectedItem().toString()).
                        observe(lifecycleOwner, new Observer<ProduitCondition>() {
                    @Override
                    public void onChanged(ProduitCondition produitCondition) {
                        modifiedListProduit.get(rvPosition).setBoId(newSelectedCondition.getIdart());

                    }
                });


                 */

               modifiedListProduit.get(rvPosition).setIdArtConditionnement(newSelectedCondition.getIdart());

                modifiedListProduit.get(rvPosition).
                        setSelectedProductTotalPrice(
                            Integer.valueOf(holder.qte_Tv.getText().toString()) * Double.valueOf(holder.unitPriceValTv.getText().toString())
                        );


                modifiedListProduitLiveData.postValue(modifiedListProduit);



            }

        });


    }

    @Override
    public int getItemCount() {
        return listProduit.size();
    }

    public static class PanierVH extends RecyclerView.ViewHolder {
        ImageView panier_produit_Iv;
        TextView nomProdPanier_Tv, promoPanier_tv, totalVal_tv, qte_Tv,stockValTv,unitPriceValTv;
        ImageView moins_Iv,plus_Iv;
        Spinner   conditionSpinner ;

        int i=1;

        public PanierVH(@NonNull View itemView, com.example.deliveryboy.Adapters.quantiteInterface quantiteInterface) {
            super(itemView);
            conditionSpinner = itemView.findViewById(R.id.conditionSpinner);

            unitPriceValTv = itemView.findViewById(R.id.unitPriceValTv);
            stockValTv = itemView.findViewById(R.id.promoPanierVal_tv);
            panier_produit_Iv = itemView.findViewById(R.id.panier_produit_Iv);
            nomProdPanier_Tv = itemView.findViewById(R.id.nomProdPanier_Tv);
            promoPanier_tv = itemView.findViewById(R.id.promoPanier_tv);
            totalVal_tv = itemView.findViewById(R.id.totalVal_tv);
            qte_Tv = itemView.findViewById(R.id.qte_Tv);
            moins_Iv = itemView.findViewById(R.id.moins_Iv);
            plus_Iv = itemView.findViewById(R.id.plus_Iv);



            plus_Iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    i++;
                    qte_Tv.setText(String.valueOf(i));
                    quantiteInterface.onValidQte(i);

                     */

                }
            });
            quantiteInterface.onValidQte(i);

            moins_Iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    /*
                    i--;
                    qte_Tv.setText(String.valueOf(i));
                    if(i<2){
                        i=1;
                        qte_Tv.setText("1");
                        quantiteInterface.onValidQte(i);
                    }

                     */
                }
            });
        }
    }

    public MutableLiveData<List<SelectedProduit>> getModifiedListProduit() {
        return modifiedListProduitLiveData;
    }
}
