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
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;

import java.util.List;

public class ProduitRvAdapter extends RecyclerView.Adapter<ProduitRvAdapter.ProduitVh> {
    Context context;
    List<Produit> produitList;


    public ProduitRvAdapter(Context context, List<Produit> produitList) {
        this.context = context;
        this.produitList = produitList;
    }

    @NonNull
    @Override
    public ProduitVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.produit_rv_item,parent,false);

        return new ProduitRvAdapter.ProduitVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitVh holder, int position) {
        holder.nomProduit_Tv.setText(produitList.get(position).getNomProduit());
        holder.produit_Iv.setImageResource(produitList.get(position).getImageProduit());
        holder.promotion_Tv.setText(produitList.get(position).getTypePromotion());
        holder.prix_Tv.setText(String.valueOf(produitList.get(position).getPrixProduit()));
        holder.qte_Tv.setText(String.valueOf(produitList.get(position).getQuantiteProduit()));
    }

    @Override
    public int getItemCount() {
        return produitList.size();
    }

    public static class ProduitVh extends RecyclerView.ViewHolder {
        ImageView produit_Iv,moins_Iv,plus_Iv;
        TextView pack_Tv,qte_surSomme_Tv,nomProduit_Tv,promotion_Tv,prix_Tv,qte_Tv;

        public ProduitVh(@NonNull View itemView) {
            super(itemView);
            produit_Iv=itemView.findViewById(R.id.produit_Iv);
            moins_Iv=itemView.findViewById(R.id.moins_Iv);
            plus_Iv=itemView.findViewById(R.id.plus_Iv);
            pack_Tv=itemView.findViewById(R.id.pack_Tv);
            qte_surSomme_Tv=itemView.findViewById(R.id.qte_surSomme_Tv);
            nomProduit_Tv=itemView.findViewById(R.id.nomProduit_Tv);
            promotion_Tv=itemView.findViewById(R.id.promotion_Tv);
            prix_Tv=itemView.findViewById(R.id.prix_Tv);
            qte_Tv=itemView.findViewById(R.id.qte_Tv);

        }
    }

}
