package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.PanierRvAdapter;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.View.MissionsFragment.TousFragment;

import java.util.List;

public class PanierActivity extends AppCompatActivity implements quantiteInterface {
    double tot;
   RecyclerView panier_produids_rv;
   TextView totalVal_Tv;
    List<Produit> selectedProduits;
    TextView nomClient_Tv,qte_Tv;
    int quantite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        getWindow().setStatusBarColor(ContextCompat.getColor(PanierActivity.this,R.color.input_backgroud_color));

        Intent intent = getIntent();
        bindViews();
        User user = (User)  intent.getSerializableExtra("user");

        nomClient_Tv.setText(user.getNameUser());
        selectedProduits = (List<Produit>) getIntent().getSerializableExtra("lista");
        panier_produids_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        panier_produids_rv.setAdapter(new PanierRvAdapter(getApplicationContext(),selectedProduits
        ,this));

        totalVal_Tv.setText(String.valueOf(calculTotal())+" dt");


    }
    public void bindViews(){
        panier_produids_rv=findViewById(R.id.panier_produids_rv);
        totalVal_Tv=findViewById(R.id.totalVal_Tv);
        nomClient_Tv=findViewById(R.id.nomClient_Tv);
        qte_Tv=findViewById(R.id.qte_Tv);
    }

    public double calculTotal() {
        for (Produit produit : selectedProduits) {
            tot=tot+produit.getTotalPrix();
        }
        return tot;
    }

    @Override
    public void onValidQte(int qte) {
        quantite = qte;
    }
}