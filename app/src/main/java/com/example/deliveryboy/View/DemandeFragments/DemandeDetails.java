package com.example.deliveryboy.View.DemandeFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.CmdLigneAdapter.CmdLigneRvAdapter;
import com.example.deliveryboy.Adapters.PanierRvAdapter;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Requests.PointageItems;
import com.example.deliveryboy.Model.Requests.PointagePostBody;
import com.example.deliveryboy.Model.Responses.CmdLigne;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;
import com.example.deliveryboy.Model.SelectedProduit;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.View.PanierActivity;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DemandeDetails extends AppCompatActivity {

    private TextView dateTv, numCmdTv, statytTv, nbArticleTv, totalVal_Tv;
    private GETDemandeChargementRes intentedDemande;

    private RecyclerView articlesRecyclerView;
    private CmdLigneRvAdapter cmdLigneRvAdapter;

    private ProgressBar progressBar;

    private Double totalPanier ;

    private MaterialButton valid_btn;
    private List<CmdLigne> cmdLignesList ;

    private List<CmdLigne> modifiedCmdLignes ;
    private DemandeChargViewModel demandeChargViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_details);

        bindViews();
        DisplayData();

        uiListeners();
    }

    private void uiListeners() {

    }

    private void DisplayData() {
        demandeChargViewModel = new DemandeChargViewModel();
        dateTv.setText(intentedDemande.getDO_Date());
        numCmdTv.setText(intentedDemande.getNumCmd());
        statytTv.setText(intentedDemande.getStatut());
        totalVal_Tv.setText(String.valueOf(intentedDemande.getDO_TotalHT()));

        demandeChargViewModel.getLocalCmdLignes(getApplicationContext(), intentedDemande.getBoId()).observe(DemandeDetails.this, new Observer<List<CmdLigne>>() {
            @Override
            public void onChanged(List<CmdLigne> cmdLigneList) {
                if(cmdLigneList!=null){

                    cmdLignesList = cmdLigneList;

                    nbArticleTv.setText(String.valueOf(cmdLigneList.size()));



                    cmdLigneRvAdapter   = new CmdLigneRvAdapter(
                            DemandeDetails.this,
                            getApplicationContext(),
                            cmdLigneList,null,null);

                    articlesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    articlesRecyclerView.setAdapter(cmdLigneRvAdapter);


                    cmdLigneRvAdapter.getModifiedListProduit().observe(DemandeDetails.this, new Observer<List<CmdLigne>>() {
                        @Override
                        public void onChanged(List<CmdLigne> cmdLigneList) {
                            try {
                                modifiedCmdLignes = cmdLigneList;


                                totalPanier = 0.0;

                                for(CmdLigne selectedProduit : cmdLigneList){
                                    totalPanier = totalPanier+selectedProduit.getDemandedTotalPrice();
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
                                valid_btn.setVisibility(View.GONE);

                            }else {
                                valid_btn.setVisibility(View.VISIBLE);

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

                List<PointageItems> pointageItems = new ArrayList<>();

                for(CmdLigne cmdLigne : cmdLignesList){
                    pointageItems.add(new PointageItems(1022, cmdLigne.getStock(), cmdLigne.getDemandedTotalPrice()));
                }

                PointagePostBody pointagePostBody = new PointagePostBody(intentedDemande.getBoId(), 1022,pointageItems);



                demandeChargViewModel.sendPointageArticles(getApplicationContext(),pointagePostBody).observe(DemandeDetails.this
                        , new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                if(aBoolean){
                                    Intent intent = new Intent(DemandeDetails.this, BottomNagContainerActivity.class);
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(DemandeDetails.this, BottomNagContainerActivity.class);
                                    startActivity(intent);                                }
                            }
                        });
            }
        });



    }

    private void bindViews() {
        intentedDemande = (GETDemandeChargementRes) getIntent().getSerializableExtra("demandeIntentExtra");

        dateTv = findViewById(R.id.dateTv);
        numCmdTv = findViewById(R.id.numCmdValueTv);
        statytTv = findViewById(R.id.statytTv);
        nbArticleTv = findViewById(R.id.nbArticleValueTv);

        articlesRecyclerView = findViewById(R.id.panier_produids_rv);

        progressBar = findViewById(R.id.progressBar);
        totalVal_Tv = findViewById(R.id.totalVal_Tv);

        valid_btn = findViewById(R.id.valid_btn);

    }
    private void uiSetup(){

    }
}