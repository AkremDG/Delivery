package com.example.deliveryboy.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.ProduitRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PassCommandeActivity extends AppCompatActivity implements  RvInterface, quantiteInterface {
    RecyclerView typeCmd_Rv,produids_rv;
    List<TypeCommande> typeCommandeList ;

    ImageView panier_pass_cmd_Iv;
    List<Produit> listProduits ;

    List<Produit> selectedProduits;


    TextView nomClient_Tv;
    int quantite;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_commande);

        getWindow().setStatusBarColor(ContextCompat.getColor(PassCommandeActivity.this,R.color.input_backgroud_color));


        Intent intent = getIntent();

         user = (User)  intent.getSerializableExtra("user");


        bindViews();
        onClickHandler();
        typeCommandeList=new ArrayList<>();

        selectedProduits=new ArrayList<>();

        TypeCommande typeCommande = new TypeCommande(R.drawable.soda_img,"Liquides");
        TypeCommande typeCommande2 = new TypeCommande(R.drawable.fish_img,"Fish");
        TypeCommande typeCommande3 = new TypeCommande(R.drawable.bread_img,"Breads");

        typeCommandeList.add(typeCommande);
        typeCommandeList.add(typeCommande2);
        typeCommandeList.add(typeCommande3);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeCmd_Rv.setLayoutManager(layoutManager);


        typeCmd_Rv.setAdapter(new TypeCmdRvAdapter(getApplicationContext(),typeCommandeList,this));


        //////////////////////////////////// RV PRODUITS
        listProduits= new ArrayList<>();

        Produit produit = new Produit(R.drawable.lait_img,"Lait demi ecrémée",200,5,"Promotion Type",true,0);
        Produit produit2 = new Produit(R.drawable.bread_img,"Pain complet",10,1,"Promotion gratuité",false,0);
        Produit produit3= new Produit(R.drawable.fish_img,"Poisson",15,1,"Promotion gratuité",true,0);
        Produit produit4= new Produit(R.drawable.fish_img,"Poisson",15,1,"Promotion gratuité",true,0);


        listProduits.add(produit2);
        listProduits.add(produit2);
        listProduits.add(produit4);
        listProduits.add(produit3);
        listProduits.add(produit);
        listProduits.add(produit2);
        listProduits.add(produit2);
        listProduits.add(produit4);
        listProduits.add(produit3);
        listProduits.add(produit);

        produids_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        produids_rv.setAdapter(new ProduitRvAdapter(getApplicationContext(),listProduits,this,this));

        nomClient_Tv.setText(user.getNameUser());
    }

    public void bindViews(){
        typeCmd_Rv=findViewById(R.id.typeCmd_Rv);
        produids_rv=findViewById(R.id.produids_rv);
        panier_pass_cmd_Iv=findViewById(R.id.panier_pass_cmd_Iv);
        nomClient_Tv=findViewById(R.id.nomClient_Tv);
    }

    public void onClickHandler(){
        panier_pass_cmd_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassCommandeActivity.this,PanierActivity.class);
                intent.putExtra("lista", (Serializable) selectedProduits);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        showAlert(position);
    }



    public void showAlert(int pos){


        final Dialog dialog = new Dialog(PassCommandeActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sheet_desc_produit);

        TextView disponibiliteTv = dialog.findViewById(R.id.disponibilite_Tv);
        if(listProduits.get(pos).getDispProduit()==false){
            disponibiliteTv.setText(String.valueOf("epuisé"));
            disponibiliteTv.setBackgroundColor(getResources().getColor(R.color.error_red));
        }

        TextView nomProduit = dialog.findViewById(R.id.nomProduit_Tv);
        nomProduit.setText(listProduits.get(pos).getNomProduit());


        TextView typePromotion = dialog.findViewById(R.id.promo_val_Tv);
        typePromotion.setText(listProduits.get(pos).getTypePromotion());


        ImageView imageProduit = dialog.findViewById(R.id.produit_Iv);
        imageProduit.setImageResource(listProduits.get(pos).getImageProduit());

        TextView prixUnitaire = dialog.findViewById(R.id.prix_val_Tv);
        prixUnitaire.setText(String.valueOf(listProduits.get(pos).getPrixProduit())+" dt");


        ImageView plus=dialog.findViewById(R.id.plus_Iv);
        ImageView moins=dialog.findViewById(R.id.moins_Iv);


        TextView qte_Tv=dialog.findViewById(R.id.qte_Tv);
        qte_Tv.setText(String.valueOf(quantite));


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantite++;
                qte_Tv.setText(String.valueOf(quantite));
            }
        });
        moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantite--;
                qte_Tv.setText(String.valueOf(quantite));
            }
        });
        MaterialButton btn = dialog.findViewById(R.id.aj_panier_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                  double totl=  quantite*listProduits.get(pos).getPrixProduit();

                  Produit produit = new Produit(listProduits.get(pos).getImageProduit(),
                            listProduits.get(pos).getNomProduit(),
                            listProduits.get(pos).getPrixProduit(),
                            quantite,
                            listProduits.get(pos).getTypePromotion(),
                            listProduits.get(pos).getDispProduit(),totl);

                    selectedProduits.add(produit);

                    Toast.makeText(PassCommandeActivity.this, "Bien ajouté !", Toast.LENGTH_SHORT).show();


                }catch (Exception e)
                {
                    Toast.makeText(PassCommandeActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                }

            }
        });





        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);




    }

    @Override
    public void onValidQte(int qte) {
        quantite=qte;

    }

}

