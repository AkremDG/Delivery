package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.utils.Utils;
import com.example.deliveryboy.Adapters.PanierCallbacks;
import com.example.deliveryboy.Adapters.PanierRvAdapter;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.DemandeProduitItem;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.SelectedProduit;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.InternetChecker;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.DemandeFragments.CreateDemande;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PanierActivity extends AppCompatActivity implements quantiteInterface, PanierCallbacks {
    int id;

    Double totalPanier;
    private ConstraintLayout rootView;
    private double tot;
    private RecyclerView panier_produids_rv;
    private MaterialButton valid_btn, annuler_btn;
    MaterialToolbar panierToolbar;
    private TextView totalVal_Tv,totalPanierHint_tv;
    private AppBarLayout appBarLayout;
    private List<SelectedProduit> selectedProduits;
    private TextView nomClient_Tv, qte_Tv,noCmdHint_tv;
    private int quantite;
    private ImageView arrow_pass_cmd_Iv;
    private DemandeChargViewModel demandeChargViewModel;
    private ProgressBar progressBar;
    private Spinner conditionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        bindViews();
        DisplayData();
        uiSetup();

        uiListeners();
    }

    private void uiSetup() {
        if(selectedProduits.size()==0){
            panier_produids_rv.setVisibility(View.INVISIBLE);

            totalPanierHint_tv.setVisibility(View.INVISIBLE);
            totalVal_Tv.setVisibility(View.INVISIBLE);
            annuler_btn.setVisibility(View.INVISIBLE);
            valid_btn.setVisibility(View.INVISIBLE);
            noCmdHint_tv.setVisibility(View.VISIBLE);
        }else {
            noCmdHint_tv.setVisibility(View.INVISIBLE);

            panier_produids_rv.setVisibility(View.VISIBLE);

            totalPanierHint_tv.setVisibility(View.VISIBLE);
            totalVal_Tv.setVisibility(View.VISIBLE);
            annuler_btn.setVisibility(View.VISIBLE);
            valid_btn.setVisibility(View.VISIBLE);


        }
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
        progressBar = findViewById(R.id.progressBar);
        rootView = findViewById(R.id.constraintRoot);
        totalPanierHint_tv = findViewById(R.id.totalPanierHint_tv);

        noCmdHint_tv = findViewById(R.id.noCmdHint_tv);
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
        panier_produids_rv.setAdapter(new PanierRvAdapter(
                PanierActivity.this,
                getApplicationContext(),
                selectedProduits,
                this,
                this));

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

                InternetChecker.isConnectedToInternet(PanierActivity.this);

                if(InternetChecker.isConnectedToInternet(PanierActivity.this)){



                    progressBar.setVisibility(View.VISIBLE);
                    panier_produids_rv.setVisibility(View.INVISIBLE);

                    totalPanierHint_tv.setVisibility(View.INVISIBLE);
                    totalVal_Tv.setVisibility(View.INVISIBLE);
                    annuler_btn.setVisibility(View.INVISIBLE);
                    annuler_btn.setVisibility(View.INVISIBLE);
                    valid_btn.setVisibility(View.INVISIBLE);



                    id++;

                    List<DemandeProduitItem> demandeProduitItemList = new ArrayList<>();




                    for(SelectedProduit selectedProduit : selectedProduits){
                        DemandeProduitItem demandeProduitItem = new DemandeProduitItem(
                                id,
                                Integer.valueOf(selectedProduit.getBoId()) ,
                                selectedProduit.getSelectedProductPrice(),
                                selectedProduit.getSelectedProductQuantity() );
                        demandeProduitItemList.add(demandeProduitItem);
                    }

                    Demande demande = new Demande(demandeProduitItemList, totalPanier);

                    Log.i("DEMAAAAAAAAAND", String.valueOf(demande));
                    demandeChargViewModel.sendDemandApi(PanierActivity.this, demande).observe(PanierActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                progressBar.setVisibility(View.INVISIBLE);

                                Intent intent = new Intent(PanierActivity.this,BottomNagContainerActivity.class);
                                startActivity(intent);

                            }else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                panier_produids_rv.setVisibility(View.VISIBLE);

                                totalPanierHint_tv.setVisibility(View.VISIBLE);
                                totalVal_Tv.setVisibility(View.VISIBLE);
                                annuler_btn.setVisibility(View.VISIBLE);
                                valid_btn.setVisibility(View.VISIBLE);

                                UiUtils.showSnackbar(rootView,"Erreur d'envoie, réessayer plus tard","Fermer");

                            }
                        }
                    });




                }else {
                    UiUtils.showSnackbar(rootView,"Erreur : pas de connexion internet","Fermer");
                }



            }
        });
        annuler_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanierActivity.this, CreateDemande.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void totalPanierCallback(Double totalPanier) {
        totalVal_Tv.setText(String.valueOf(String.valueOf(totalPanier)));

    }
}