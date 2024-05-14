package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.PanierRvAdapter;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.DemandeProduitItem;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.SelectedProduit;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.View.DemandeFragments.CreateDemande;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PanierActivity extends AppCompatActivity implements quantiteInterface {
    int id;

    Double totalPanier;
    private double tot;
    private RecyclerView panier_produids_rv;
    private MaterialButton valid_btn, annuler_btn;
    MaterialToolbar panierToolbar;
    private TextView totalVal_Tv;
    private AppBarLayout appBarLayout;
    private List<SelectedProduit> selectedProduits;
    private TextView nomClient_Tv, qte_Tv;
    private int quantite;
    private ImageView arrow_pass_cmd_Iv;
    private DemandeChargViewModel demandeChargViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        bindViews();
        DisplayData();
        uiListeners();
    }

    public void bindViews() {
        demandeChargViewModel = new DemandeChargViewModel();
        panierToolbar = findViewById(R.id.panierToolbar);
        panier_produids_rv = findViewById(R.id.panier_produids_rv);
        appBarLayout = findViewById(R.id.panierAppBar);
        totalVal_Tv = findViewById(R.id.totalVal_Tv);
        nomClient_Tv = findViewById(R.id.nomClient_Tv);
        qte_Tv = findViewById(R.id.qte_Tv);
        arrow_pass_cmd_Iv = findViewById(R.id.arrow_pass_cmd_Iv);
        valid_btn = findViewById(R.id.valid_btn);
        annuler_btn = findViewById(R.id.annuler_btn);
    }

    public double calculTotal() {

        return 0;
    }

    @Override
    public void onValidQte(int qte) {
        quantite = qte;
    }

    public void DisplayData() {
        Intent intent = getIntent();

        selectedProduits = (List<SelectedProduit>) getIntent().getSerializableExtra("selectedItems");

        Log.i("SELECTEDPRODUITS", String.valueOf(selectedProduits));

        panier_produids_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        panier_produids_rv.setAdapter(new PanierRvAdapter(getApplicationContext(), selectedProduits
                , this));

         totalPanier = 0.0;

        for(SelectedProduit selectedProduit : selectedProduits){
            totalPanier = totalPanier + selectedProduit.getSelectedProductTotalPrice();
        }
        DecimalFormat df = new DecimalFormat("#.###");
        String formattedTotalPanier = df.format(totalPanier);

        totalVal_Tv.setText(String.valueOf(formattedTotalPanier));

    }

    public void uiListeners() {

        panierToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanierActivity.this,CreateDemande.class);
                startActivity(intent);
            }
        });
        valid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PanierActivity.this, "VALIDER", Toast.LENGTH_SHORT).show();

                id++;


                List<DemandeProduitItem> demandeProduitItemList = new ArrayList<>();

                        for(SelectedProduit selectedProduit : selectedProduits){
                            DemandeProduitItem demandeProduitItem = new DemandeProduitItem(id,Integer.valueOf(selectedProduit.getBoId()) ,
                                    selectedProduit.getSelectedProductPrice(), selectedProduit.getSelectedProductQuantity() );
                            demandeProduitItemList.add(demandeProduitItem);
                        }


                    Demande demande = new Demande(demandeProduitItemList, totalPanier);


                Log.i("DEMAAAAAAAAAND", String.valueOf(demande));
                demandeChargViewModel.sendDemandApi(PanierActivity.this, demande).observe(PanierActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            Toast.makeText(PanierActivity.this, "SENDD OKKKKK", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(PanierActivity.this, "SENDD ERRRROR", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        annuler_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PanierActivity.this, "ANNULER", Toast.LENGTH_SHORT).show();
            }
        });
    }
}