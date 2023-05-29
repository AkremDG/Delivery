package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.View.MissionsFragment.TousFragment;

import java.util.ArrayList;
import java.util.List;

public class PassCommandeActivity extends AppCompatActivity implements RvInterface {
    RecyclerView typeCmd_Rv;
    List<TypeCommande> typeCommandeList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_commande);

        getWindow().setStatusBarColor(ContextCompat.getColor(PassCommandeActivity.this,R.color.white));


        bindViews();

        typeCommandeList=new ArrayList<>();

        TypeCommande typeCommande = new TypeCommande(R.drawable.soda_img,"Liquides");
        TypeCommande typeCommande2 = new TypeCommande(R.drawable.fish_img,"Fish");
        TypeCommande typeCommande3 = new TypeCommande(R.drawable.bread_img,"Breads");
        TypeCommande typeCommande4 = new TypeCommande(R.drawable.pizz_image,"Pizza");
        TypeCommande type5 = new TypeCommande(R.drawable.app_logo,"Other");

        typeCommandeList.add(typeCommande);
        typeCommandeList.add(typeCommande2);
        typeCommandeList.add(typeCommande3);
        typeCommandeList.add(typeCommande4);
        typeCommandeList.add(type5);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeCmd_Rv.setLayoutManager(layoutManager);


        typeCmd_Rv.setAdapter(new TypeCmdRvAdapter(getApplicationContext(),typeCommandeList,this));


    }

    public void bindViews(){
        typeCmd_Rv=findViewById(R.id.typeCmd_Rv);
    }

    @Override
    public void onItemClick(int position) {

    }
}