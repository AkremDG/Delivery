package com.example.deliveryboy.View.DemandeFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.utils.Utils;
import com.example.deliveryboy.Adapters.CmdLigneAdapter.CmdLigneRvAdapter;
import com.example.deliveryboy.Adapters.PanierRvAdapter;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Requests.PointageItems;
import com.example.deliveryboy.Model.Requests.PointagePostBody;
import com.example.deliveryboy.Model.Responses.CmdLigne;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;
import com.example.deliveryboy.Model.SelectedProduit;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.InternetChecker;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.View.BottomNavFragments.DemandeFragment;
import com.example.deliveryboy.View.PanierActivity;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DemandeDetails extends AppCompatActivity {

    private boolean isModifiedDemande=false;


    private TextView dateTv, numCmdTv, statytTv, nbArticleTv, totalVal_Tv;
    private GETDemandeChargementRes intentedDemande;

    private RecyclerView articlesRecyclerView;
    private CmdLigneRvAdapter cmdLigneRvAdapter;

    private ProgressBar progressBar;

    private View rootView;

    private Double totalPanier ;

    private MaterialButton valid_btn,annuler_btn;
    private List<CmdLigne> cmdLignesList ;

    private List<CmdLigne> modifiedCmdLignes ;
    private DemandeChargViewModel demandeChargViewModel;

    private MaterialToolbar detailsDemande_mt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_details);

        bindViews();
        DisplayData();


        uiListeners();
    }

    private void uiListeners() {
        detailsDemande_mt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemandeDetails.this, BottomNagContainerActivity.class);
                startActivity(intent);
            }
        });

    }

    private void DisplayData() {
        demandeChargViewModel = new DemandeChargViewModel();
        dateTv.setText(intentedDemande.getDO_Date());
        numCmdTv.setText(intentedDemande.getNumCmd());
        statytTv.setText(intentedDemande.getStatut());
        totalVal_Tv.setText(String.valueOf(intentedDemande.getDO_TotalHT()));

        if(!intentedDemande.getStatut().equals("Cloturée")){
            valid_btn.setVisibility(View.GONE);
        }

        demandeChargViewModel.getLocalCmdLignes(getApplicationContext(), intentedDemande.getBoId()).observe(DemandeDetails.this, new Observer<List<CmdLigne>>() {
            @Override
            public void onChanged(List<CmdLigne> cmdLigneList) {
                if(cmdLigneList!=null){

                    cmdLignesList = cmdLigneList;

                    nbArticleTv.setText(String.valueOf(cmdLigneList.size()));



                    cmdLigneRvAdapter   = new CmdLigneRvAdapter(intentedDemande.getStatut(),
                            DemandeDetails.this,
                            getApplicationContext(),
                            cmdLigneList,null,null);

                    articlesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    articlesRecyclerView.setAdapter(cmdLigneRvAdapter);


                    cmdLigneRvAdapter.getModifiedListProduit().observe(DemandeDetails.this, new Observer<List<CmdLigne>>() {
                        @Override
                        public void onChanged(List<CmdLigne> cmdLigneList) {
                            try {


                                nbArticleTv.setText(String.valueOf(cmdLigneList.size()));

                                isModifiedDemande = true;

                                modifiedCmdLignes = cmdLigneList;



                                totalPanier = 0.0;

                                for(CmdLigne selectedProduit : cmdLigneList){
                                    totalPanier = totalPanier+selectedProduit.getDemandedTotalPrice();

                                    Log.i("MODIFIIIIIED", selectedProduit.getArticleDesignation());
                                }

                                DecimalFormat df = new DecimalFormat("#.###");
                                String formattedTotalPanier = df.format(totalPanier);

                                totalVal_Tv.setText(String.valueOf(formattedTotalPanier));

                            }catch (Exception e){

                            }


                        }
                    });


                    cmdLigneRvAdapter.getIsErrorDetectedLiveData().observe(DemandeDetails.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                if(intentedDemande.getStatut().equals("Cloturée")){
                                    valid_btn.setVisibility(View.GONE);
                                }

                            }else {
                                if(intentedDemande.getStatut().equals("Cloturée")){
                                    valid_btn.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    });

                }else{
                    Log.i("LIGNEEEE","nulllllllllll");

                }
            }
        });

        valid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);


                if(InternetChecker.isConnectedToInternet(DemandeDetails.this)){
                    if(isModifiedDemande){

                        List<PointageItems> pointageItems = new ArrayList<>();

                        for(CmdLigne cmdLigne : modifiedCmdLignes){
                            pointageItems.add(new PointageItems(cmdLigne.getIdart(), cmdLigne.getDemandedQuantity(), cmdLigne.getDemandedTotalPrice()));
                        }

                        PointagePostBody pointagePostBody = new PointagePostBody(intentedDemande.getBoId(), intentedDemande.getId_car(),pointageItems);

                        Log.i("CHANGEDDDDDDDDEMANDE", pointagePostBody.toString());




                        demandeChargViewModel.sendPointageArticles(getApplicationContext(),pointagePostBody).observe(DemandeDetails.this
                                , new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean aBoolean) {
                                        if(aBoolean){
                                            progressBar.setVisibility(View.GONE);


                                            Intent intent = new Intent(DemandeDetails.this, BottomNagContainerActivity.class);
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                            startActivity(intent);
                                        }else {
                                            progressBar.setVisibility(View.GONE);

                                            UiUtils.showSnackbar(rootView,"Erreur : réesayer plus tard","Fermer");

                                        }
                                    }
                                });






                    }else {
                        List<PointageItems> pointageItems = new ArrayList<>();

                        for(CmdLigne cmdLigne : cmdLignesList){
                            pointageItems.add(new PointageItems(cmdLigne.getIdart(), cmdLigne.getStock(), cmdLigne.getDemandedTotalPrice()));
                        }

                        PointagePostBody pointagePostBody = new PointagePostBody(intentedDemande.getBoId(), intentedDemande.getId_car(),pointageItems);

                        Log.i("SAMEEEEEEEEDEMANDEEEEE", pointagePostBody.toString());

                        demandeChargViewModel.sendPointageArticles(getApplicationContext(),pointagePostBody).observe(DemandeDetails.this
                                , new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean aBoolean) {
                                        if(aBoolean){
                                            progressBar.setVisibility(View.GONE);

                                            Intent intent = new Intent(DemandeDetails.this, BottomNagContainerActivity.class);
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                                            startActivity(intent);
                                        }else {
                                            progressBar.setVisibility(View.GONE);


                                            Toast.makeText(DemandeDetails.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                }else {
                    progressBar.setVisibility(View.GONE);

                    UiUtils.showSnackbar(rootView,"Erreur : Pas de connexion internet","Fermer");
                }



            }
        });



    }

    private void bindViews() {
        detailsDemande_mt = findViewById(R.id.detailsDemande_mt);
        intentedDemande = (GETDemandeChargementRes) getIntent().getSerializableExtra("demandeIntentExtra");

        dateTv = findViewById(R.id.dateTv);
        numCmdTv = findViewById(R.id.numCmdValueTv);
        statytTv = findViewById(R.id.statytTv);
        nbArticleTv = findViewById(R.id.nbArticleValueTv);

        articlesRecyclerView = findViewById(R.id.panier_produids_rv);

        progressBar = findViewById(R.id.progressBar);
        totalVal_Tv = findViewById(R.id.totalVal_Tv);

        valid_btn = findViewById(R.id.valid_btn);
        annuler_btn = findViewById(R.id.annuler_btn);
        annuler_btn.setVisibility(View.GONE);

        rootView = findViewById(R.id.rootView);


    }
    private void uiSetup(){

    }
}