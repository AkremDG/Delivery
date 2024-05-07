package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.PanierRvAdapter;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.View.DemandeFragments.CreateDemande;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class PanierActivity extends AppCompatActivity implements quantiteInterface {
    private double tot;
    private RecyclerView panier_produids_rv;
    private MaterialButton valid_btn, annuler_btn;
    private TextView totalVal_Tv;
    private List<Produit> selectedProduits;
    private TextView nomClient_Tv, qte_Tv;
    private int quantite;
    private ImageView arrow_pass_cmd_Iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        DisplayData();
        uiListeners();
    }

    public void bindViews() {
        panier_produids_rv = findViewById(R.id.panier_produids_rv);
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
        bindViews();
        User user = (User) intent.getSerializableExtra("user");
        nomClient_Tv.setText(user.getEmail());
        selectedProduits = (List<Produit>) getIntent().getSerializableExtra("lista");
        panier_produids_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        panier_produids_rv.setAdapter(new PanierRvAdapter(getApplicationContext(), selectedProduits
                , this));
        totalVal_Tv.setText(String.valueOf(calculTotal()) + " dt");

    }

    public void uiListeners() {
        arrow_pass_cmd_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PanierActivity.this, CreateDemande.class);
                startActivity(intent);
            }
        });
        valid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PanierActivity.this, "VALIDER", Toast.LENGTH_SHORT).show();
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