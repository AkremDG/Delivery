package com.example.deliveryboy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.R;

import java.util.List;

public class TypeCmdRvAdapter extends RecyclerView.Adapter<TypeCmdRvAdapter.TypeCmdVh>   {



    Context context;
    List<TypeCommande> typeCommandeList;

    private final TypeProduitInterface typeProduitInterface;




    public static class TypeCmdVh extends RecyclerView.ViewHolder {
        ImageView soda_img;
        TextView type_Tv;


        public TypeCmdVh(@NonNull View itemView, TypeProduitInterface typeProduitInterface,Context context) {
            super(itemView);

            soda_img=itemView.findViewById(R.id.typeCmd_Iv);
            type_Tv=itemView.findViewById(R.id.type_Tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(typeProduitInterface != null){
                        int pos= getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                                typeProduitInterface.onTypeItemClick(pos);

                        }
                    }
                }
            });

        }
    }

    public TypeCmdRvAdapter(Context context,List<TypeCommande> typeCommandeList,TypeProduitInterface typeProduitInterface ) {
        this.context = context;
        this.typeCommandeList = typeCommandeList;
        this.typeProduitInterface=typeProduitInterface;

    }
    @NonNull
    @Override
    public TypeCmdVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.cmd_type_item,parent,false);

        return new TypeCmdRvAdapter.TypeCmdVh(view,typeProduitInterface,context);
    }


    @Override
    public void onBindViewHolder(@NonNull TypeCmdVh holder, int position) {
        holder.soda_img.setImageResource(typeCommandeList.get(position).getImage());
        holder.type_Tv.setText(typeCommandeList.get(position).getName());


    }

    @Override
    public int getItemCount() {
      return  typeCommandeList.size();
    }


}
