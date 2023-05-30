package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.R;

import java.util.List;

public class PanierRvAdapter extends RecyclerView.Adapter<PanierRvAdapter.PanierVH>{

    Context context;
    List<Produit> listProduit;

    public PanierRvAdapter(Context context, List<Produit> listProduit) {
        this.context = context;
        this.listProduit = listProduit;
    }

    @NonNull
    @Override
    public PanierVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.panier_rv_item,parent,false);

        return new PanierRvAdapter.PanierVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PanierVH holder, int position) {
        holder.panier_produit_Iv.setImageResource(listProduit.get(position).getImageProduit());
        holder.nomProdPanier_Tv.setText(listProduit.get(position).getNomProduit());
        holder.promoPanier_tv.setText(listProduit.get(position).getTypePromotion());


    }

    @Override
    public int getItemCount() {
        return listProduit.size();
    }


    public static class PanierVH extends RecyclerView.ViewHolder {
        Context context;
        ImageView panier_produit_Iv;
        TextView nomProdPanier_Tv, promoPanier_tv, totalVal_tv, qte_Tv;
        int i;

        public PanierVH(@NonNull View itemView) {
            super(itemView);

            panier_produit_Iv = itemView.findViewById(R.id.panier_produit_Iv);

            nomProdPanier_Tv = itemView.findViewById(R.id.nomProdPanier_Tv);
            promoPanier_tv = itemView.findViewById(R.id.promoPanier_tv);
            totalVal_tv = itemView.findViewById(R.id.totalVal_tv);
            qte_Tv = itemView.findViewById(R.id.qte_Tv);

        }
    }

}
