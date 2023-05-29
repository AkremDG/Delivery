package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.ProduitRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.View.MissionsFragment.TousFragment;

import java.util.ArrayList;
import java.util.List;

public class PassCommandeActivity extends AppCompatActivity implements RvInterface {
    RecyclerView typeCmd_Rv,produids_rv;
    List<TypeCommande> typeCommandeList ;

    List<Produit> listProduits ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_commande);

        getWindow().setStatusBarColor(ContextCompat.getColor(PassCommandeActivity.this,R.color.input_backgroud_color));



        bindViews();

        typeCommandeList=new ArrayList<>();

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

        Produit produit = new Produit(R.drawable.lait_img,"Lait demi ecrémée",200,5,"Promotion Type");
        Produit produit2 = new Produit(R.drawable.bread_img,"Pain complet",10,1,"Promotion gratuité");
        Produit produit3= new Produit(R.drawable.fish_img,"Poisson",15,1,"Promotion gratuité");
        Produit produit4= new Produit(R.drawable.fish_img,"Poisson",15,1,"Promotion gratuité");


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




        produids_rv.setAdapter(new ProduitRvAdapter(getApplicationContext(),listProduits));








    }

    public void bindViews(){
        typeCmd_Rv=findViewById(R.id.typeCmd_Rv);
        produids_rv=findViewById(R.id.produids_rv);
    }

    @Override
    public void onItemClick(int position) {

    }
}