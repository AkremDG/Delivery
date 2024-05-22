package com.example.deliveryboy.Adapters.CmdLigneAdapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Adapters.PanierCallbacks;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.Responses.CmdLigne;
import com.example.deliveryboy.Model.SelectedProduit;
import com.example.deliveryboy.R;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;

import java.util.ArrayList;
import java.util.List;

public class CmdLigneRvAdapter extends RecyclerView.Adapter<CmdLigneRvAdapter.PanierVH>{
    Double newTotalPanier = 0.0;
    ProduitCondition newSelectedCondition;
    Double totalPanier = 0.0;
    String selectedCondition;
    Context context;
    List<CmdLigne> listProduit;
    List<CmdLigne> modifiedListProduit;

    com.example.deliveryboy.Adapters.quantiteInterface quantiteInterface;
    int quantite;
    LifecycleOwner lifecycleOwner;

    DemandeChargViewModel demandeChargViewModel;
    PanierCallbacks panierCallbacks;

    MutableLiveData<List<CmdLigne>> modifiedListProduitLiveData;

    MutableLiveData<Boolean> isErrorDetectedLiveData;


    public CmdLigneRvAdapter(LifecycleOwner lifecycleOwner, Context context, List<CmdLigne> listProduit,
                             quantiteInterface quantiteInterface, PanierCallbacks panierCallbacks) {
        this.context = context;
        this.listProduit = listProduit;
        this.quantiteInterface=quantiteInterface;
        this.lifecycleOwner = lifecycleOwner;
        this.panierCallbacks = panierCallbacks;
        demandeChargViewModel = new DemandeChargViewModel();
        modifiedListProduitLiveData = new MutableLiveData<>();
        isErrorDetectedLiveData = new MutableLiveData<>();
        modifiedListProduit = listProduit;

    }

    @NonNull
    @Override
    public PanierVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.panier_rv_item,parent,false);

        return new CmdLigneRvAdapter.PanierVH(view, this.quantiteInterface);
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public void onBindViewHolder(@NonNull PanierVH holder, int position) {

        int rvPosition = position;


        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    modifiedListProduit.get(rvPosition).setDemandedTotalPrice(
                            0d
                    );
                    modifiedListProduitLiveData.postValue(modifiedListProduit);


                    listProduit.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listProduit.size());


                }
            }
        });


        holder.unitPriceValTv.setText(String.valueOf(listProduit.get(position).getPuht()));

        holder.panier_produit_Iv.setImageResource(R.drawable.bag);

        holder.nomProdPanier_Tv.setText(listProduit.get(position).getArticleDesignation());

        holder.stockValTv.setText(String.valueOf(listProduit.get(position).getStock()));

        holder.totalVal_tv.setText(String.valueOf(listProduit.get(position).getDemandedTotalPrice()));

        try {
            holder.qte_Tv.setText(String.valueOf(listProduit.get(position).getDemandedQuantity()));

        }catch (Exception e){

        }


        int itemSelectedQuantitty =1 ;



        holder.qte_Tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {

                    Integer selectedQuantity = Integer.valueOf(s.toString());

                    String strStock = holder.stockValTv.getText().toString();

                    int formattedActualStock = (int) Math.floor(Double.valueOf(strStock));


                    if(selectedQuantity>formattedActualStock){


                        holder.qte_Tv.setText(String.valueOf(holder.stockValTv.getText().toString()));
                        holder.qte_Tv.setTextColor(context.getResources().getColor(R.color.orange_btn_color));
                        holder.errorQteTv.setVisibility(View.VISIBLE);



                        holder.qte_Tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                holder.qte_Tv.setText("");

                            }
                        });



                    }
                    else if (selectedQuantity<=0){
                        holder.qte_Tv.setTextColor(Color.parseColor("#FFA5A5A5"));
                        holder.errorQteTv.setVisibility(View.GONE);
                        holder.qte_Tv.setText("1");

                    }
                    else {
                        holder.qte_Tv.setTextColor(Color.parseColor("#FFA5A5A5"));
                        holder.errorQteTv.setVisibility(View.GONE);

                    }


                    Double totalProductPrice = Integer.valueOf(holder.qte_Tv.getText().toString()) * Double.valueOf(holder.unitPriceValTv.getText().toString());

                    holder.totalVal_tv.setText(String.valueOf(String.valueOf(totalProductPrice)));





                    ///////////////////////////

                }catch (Exception e){


                }




            }

            @Override
            public void afterTextChanged(Editable s) {



                if(s.toString().equals("")){
                    holder.errorQteTv.setVisibility(View.VISIBLE);
                    holder.errorQteTv.setText("Choisir la quantitée !");
                    isErrorDetectedLiveData.postValue(true);
                }else {
                    holder.errorQteTv.setVisibility(View.GONE);
                    holder.errorQteTv.setText("Stock indisponible !");
                    isErrorDetectedLiveData.postValue(false);

                }

                if(!s.equals(""))
                {

                    modifiedListProduit.get(rvPosition).setDemandedQuantity(Integer.valueOf(holder.qte_Tv.getText().toString()));

                    modifiedListProduit.get(rvPosition).setDemandedTotalPrice(Double.valueOf(holder.totalVal_tv.getText().toString()));


                    modifiedListProduitLiveData.postValue(modifiedListProduit);
                }





                }



        });

        holder.totalVal_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                modifiedListProduit.get(rvPosition).setDemandedTotalPrice(Double.valueOf(s.toString()));


                modifiedListProduitLiveData.postValue(modifiedListProduit);
            }
        });


        holder.plus_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int valQuantity = Integer.valueOf(holder.qte_Tv.getText().toString());
                    valQuantity++;


                    holder.qte_Tv.setText(String.valueOf(
                            valQuantity
                    ));
                }catch (Exception e){

                }


            }
        });


        holder.moins_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int valQuantity = Integer.valueOf(holder.qte_Tv.getText().toString());
                    valQuantity--;

                    holder.qte_Tv.setText(String.valueOf(
                            valQuantity
                    ));

                }catch (Exception e){

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return listProduit.size();
    }

    public static class PanierVH extends RecyclerView.ViewHolder {
        ImageView panier_produit_Iv;
        TextView nomProdPanier_Tv, promoPanier_tv, totalVal_tv, qte_Tv,stockValTv,unitPriceValTv,errorQteTv;
        ImageView moins_Iv,plus_Iv,deleteIv;
        Spinner   conditionSpinner ;

        int i=1;

        public PanierVH(@NonNull View itemView, com.example.deliveryboy.Adapters.quantiteInterface quantiteInterface) {
            super(itemView);
            errorQteTv= itemView.findViewById(R.id.errorQteTv);
            conditionSpinner = itemView.findViewById(R.id.conditionSpinner);

            deleteIv = itemView.findViewById(R.id.deleteIv);
            unitPriceValTv = itemView.findViewById(R.id.unitPriceValTv);
            stockValTv = itemView.findViewById(R.id.promoPanierVal_tv);
            panier_produit_Iv = itemView.findViewById(R.id.panier_produit_Iv);
            nomProdPanier_Tv = itemView.findViewById(R.id.nomProdPanier_Tv);
            promoPanier_tv = itemView.findViewById(R.id.promoPanier_tv);
            totalVal_tv = itemView.findViewById(R.id.totalVal_tv);
            qte_Tv = itemView.findViewById(R.id.qte_Tv);
            moins_Iv = itemView.findViewById(R.id.moins_Iv);
            plus_Iv = itemView.findViewById(R.id.plus_Iv);


            deleteIv.setVisibility(View.VISIBLE);


        }
    }

    public MutableLiveData<List<CmdLigne>> getModifiedListProduit() {
        return modifiedListProduitLiveData;
    }

    public MutableLiveData<Boolean> getIsErrorDetectedLiveData() {
        return isErrorDetectedLiveData;
    }
}
