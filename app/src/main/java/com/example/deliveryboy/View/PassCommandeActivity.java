package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.deliveryboy.Adapters.TypeProduitInterface;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PassCommandeActivity extends AppCompatActivity implements RvInterface, quantiteInterface, TypeProduitInterface {

    private Produit produit;
    private SearchView searchBar_pass_cmd;
    private Boolean isFilterClicked = false;
    private List<Produit> filtredProducts = new ArrayList<>();
    private String typeProduit;
    private RecyclerView typeCmd_Rv, produids_rv;
    private List<TypeCommande> typeCommandeList;

    private ImageView panier_pass_cmd_Iv;
    private List<Produit> listProduits;

    private List<Produit> selectedProduits = new ArrayList<>();


    private ImageView arrow_pass_cmd_Iv;
    private TextView nomClient_Tv;
    private int quantite = 1;
    private User user;
    private int itemCickedPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_commande);

        bindViews();
        fakeProductsData();
        DisplayData(listProduits);
        uiListeners();
    }

    public void bindViews() {
        typeCmd_Rv = findViewById(R.id.typeCmd_Rv);
        produids_rv = findViewById(R.id.produids_rv);
        panier_pass_cmd_Iv = findViewById(R.id.panier_pass_cmd_Iv);
        nomClient_Tv = findViewById(R.id.nomClient_Tv);
        searchBar_pass_cmd = findViewById(R.id.searchBar_pass_cmd);
        arrow_pass_cmd_Iv = findViewById(R.id.arrow_pass_cmd_Iv);
    }

    public void uiListeners() {
        arrow_pass_cmd_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassCommandeActivity.this, BottomNagContainerActivity.class);
                startActivity(intent);
            }
        });
        panier_pass_cmd_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassCommandeActivity.this, PanierActivity.class);
                intent.putExtra("lista", (Serializable) selectedProduits);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        produids_rv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            private int scrollThreshold = 20;
            private boolean scrolledDown = false;
            private int oldScrollY = 0;

            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY + scrollThreshold) {
                    // Scrolling down
                    if (!scrolledDown) {
                        scrolledDown = true;
                        // Hide the views with animation
                        animateViewVisibilityWithFadeOut(searchBar_pass_cmd);
                        animateViewVisibilityWithFadeOut(typeCmd_Rv);
                    }
                } else if (scrollY < oldScrollY - scrollThreshold) {
                    // Scrolling up
                    if (scrolledDown) {
                        scrolledDown = false;
                        // Show the views with animation
                        animateViewVisibilityWithFadeIn(searchBar_pass_cmd);
                        animateViewVisibilityWithFadeIn(typeCmd_Rv);
                    }
                }

                oldScrollY = scrollY;
            }

            private void animateViewVisibilityWithFadeOut(final View view) {
                view.animate()
                        .alpha(0f)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                view.setVisibility(View.GONE);
                            }
                        })
                        .start();
            }

            private void animateViewVisibilityWithFadeIn(final View view) {
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0f);
                view.animate()
                        .alpha(1f)
                        .start();
            }

        });
        List<Produit> produitList = new ArrayList<>();

        searchBar_pass_cmd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() == 0) {
                    //SEARCH EMPTY
                    if (isFilterClicked == false) {
                        DisplayData(listProduits);

                    } else if (isFilterClicked == true) {

                        DisplayData(filtredProducts);

                    }

                } else {
                    boolean dataFound = false;

                    if (isFilterClicked == false) {

                        produitList.clear();

                        for (Produit produit : listProduits) {
                            if (produit.getNomProduit().toLowerCase(Locale.ROOT).contains(newText)) {
                                produitList.add(produit);
                                //DisplayData(produitList);
                                dataFound = true;

                            }
                        }
                        if (dataFound) {
                            DisplayData(produitList);
                        } else {
                            produids_rv.setAdapter(null);
                        }


                    } else if (isFilterClicked == true) {

                        produitList.clear();

                        for (Produit produit : filtredProducts) {
                            if (produit.getNomProduit().contains(newText)) {
                                produitList.add(produit);
                                // DisplayData(produitList);
                                dataFound = true;
                            }
                        }
                        if (dataFound) {
                            DisplayData(produitList);
                        } else {
                            produids_rv.setAdapter(null);
                        }
                    }


                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        quantite = 0;

        if (isFilterClicked == true) {
            showAlert(filtredProducts, position);
        } else
            showAlert(listProduits, position);

    }


    public void showAlert(List<Produit> products, int pos) {

        final Dialog dialog = new Dialog(PassCommandeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sheet_desc_produit);

        TextView disponibiliteTv = dialog.findViewById(R.id.disponibilite_Tv);

        if (products.get(pos).getDispProduit() == false) {
            disponibiliteTv.setText(String.valueOf("epuisé"));
            disponibiliteTv.setBackgroundColor(getResources().getColor(R.color.error_red));
        }

        TextView nomProduit = dialog.findViewById(R.id.nomProduit_Tv);
        nomProduit.setText(products.get(pos).getNomProduit());


        TextView typePromotion = dialog.findViewById(R.id.promo_val_Tv);
        typePromotion.setText(products.get(pos).getTypePromotion());


        ImageView imageProduit = dialog.findViewById(R.id.produit_Iv);
        imageProduit.setImageResource(products.get(pos).getImageProduit());

        TextView prixUnitaire = dialog.findViewById(R.id.prix_val_Tv);
        prixUnitaire.setText(String.valueOf(products.get(pos).getPrixProduit()) + " dt");


        ImageView plus = dialog.findViewById(R.id.plus_Iv);
        ImageView moins = dialog.findViewById(R.id.moins_Iv);


        TextView qte_Tv = dialog.findViewById(R.id.qte_Tv);
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

                    double totl = quantite * products.get(pos).getPrixProduit();

                    produit = new Produit(products.get(pos).getImageProduit(),
                            products.get(pos).getNomProduit(),
                            products.get(pos).getPrixProduit(),
                            quantite,
                            products.get(pos).getTypePromotion(),
                            products.get(pos).getDispProduit(), totl, products.get(pos).getCategorie());

                    selectedProduits.add(produit);

                    Toast.makeText(PassCommandeActivity.this, "Bien ajouté !", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
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
    /*
        quantite=qte;

     */
    }

    public void DisplayData(List<Produit> listProd) {

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        typeCommandeList = new ArrayList<>();

        TypeCommande eeeeeee = new TypeCommande(R.drawable.drink_icon, "Liquides");
        TypeCommande typezCommande2 = new TypeCommande(R.drawable.fish_icon, "Fish");
        TypeCommande typeComdmande3 = new TypeCommande(R.drawable.bread_icon, "Breads");
        TypeCommande fromage = new TypeCommande(R.drawable.fromage, "Fromages");

        typeCommandeList.add(eeeeeee);
        typeCommandeList.add(typezCommande2);
        typeCommandeList.add(typeComdmande3);
        typeCommandeList.add(fromage);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeCmd_Rv.setLayoutManager(layoutManager);
        typeCmd_Rv.setAdapter(new TypeCmdRvAdapter(getApplicationContext(), typeCommandeList, this));

        //////////////////////////////////// RV PRODUITS
        produids_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        produids_rv.setAdapter(new ProduitRvAdapter(getApplicationContext(), listProd, this, this));
        nomClient_Tv.setText(user.getEmail());
    }

    @Override
    public void onTypeItemClick(int position) {
        itemCickedPos = position;
        filtredProducts.clear();
        isFilterClicked = true;


        if (position == 0)
            typeProduit = "Liquids";
        if (position == 1)
            typeProduit = "Fish";
        if (position == 2)
            typeProduit = "Breads";

        DisplayData(filtredProducts);
    }

    public void fakeProductsData() {
        listProduits = new ArrayList<>();
        //DRINKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
        Produit coke = new Produit(R.drawable.cok1, "COKA COLA", 2, 1,
                "Promotion Type", true, 0, "Liquids");
        Produit seven = new Produit(R.drawable.seven, "SEVEN UP", 2.5, 1,
                "Promotion Type", true, 0, "Liquids");
        Produit FANTA = new Produit(R.drawable.fanta, "FANTA", 2.2, 1,
                "Promotion Type", true, 0, "Liquids");

        Produit pepsi = new Produit(R.drawable.pepsi, "PEPSI", 3, 1,
                "Promotion Type", true, 0, "Liquids");
        Produit tonic = new Produit(R.drawable.tonic, "PEPSI", 3, 1,
                "Promotion Type", true, 0, "Liquids");

        listProduits.add(coke);
        listProduits.add(seven);
        listProduits.add(FANTA);
        listProduits.add(pepsi);
        listProduits.add(tonic);
        listProduits.add(coke);
        listProduits.add(seven);
        listProduits.add(FANTA);
        listProduits.add(pepsi);
        listProduits.add(tonic);
        listProduits.add(coke);
        listProduits.add(seven);
        listProduits.add(FANTA);
        listProduits.add(pepsi);
        listProduits.add(tonic);
        listProduits.add(coke);
        listProduits.add(seven);
        listProduits.add(FANTA);
        listProduits.add(pepsi);
        listProduits.add(tonic);
        listProduits.add(coke);
        listProduits.add(seven);
        listProduits.add(FANTA);
        listProduits.add(pepsi);
        listProduits.add(tonic);

/////////////////////////////////////////////////////////

        Produit rio = new Produit(R.drawable.rio, "RIO", 3.5, 1,
                "Promotion Type", true, 0, "Fish");
        Produit century = new Produit(R.drawable.century, "CENTURY", 4.5, 1,
                "Promotion Type", true, 0, "Fish");
        Produit gourmet = new Produit(R.drawable.gourmet, "GOURMET", 2.4, 1,
                "Promotion Type", true, 0, "Fish");

        Produit tuna4 = new Produit(R.drawable.tuna1, "PEPSI", 3, 1,
                "Promotion Type", true, 0, "Fish");

        listProduits.add(rio);
        listProduits.add(gourmet);
        listProduits.add(tuna4);
        listProduits.add(rio);
        listProduits.add(gourmet);
        listProduits.add(tuna4);
        listProduits.add(gourmet);
        listProduits.add(tuna4);
        listProduits.add(rio);
        listProduits.add(gourmet);
        listProduits.add(tuna4);
        listProduits.add(rio);
        listProduits.add(gourmet);
        listProduits.add(tuna4);
        listProduits.add(gourmet);
        listProduits.add(tuna4);
        ///////////////////////////////////////////////////////// BREAD

        Produit toast = new Produit(R.drawable.toast, "TOAST", 3.5, 1,
                "Promotion Type", true, 0, "Breads");
        Produit complet = new Produit(R.drawable.complet, "COMPLET", 2.3, 1,
                "Promotion Type", true, 0, "Breads");
        Produit baget = new Produit(R.drawable.baget, "FRENCH", 1.5, 1,
                "Promotion Type", true, 0, "Breads");
        Produit baguette = new Produit(R.drawable.baguette, "FRENCH", 2.5, 1,
                "Promotion Type", true, 0, "Bread");
        Produit croissant = new Produit(R.drawable.croissant, "CROISSANT", 1.400, 1,
                "Promotion Type", true, 0, "Breads");
        listProduits.add(toast);
        listProduits.add(complet);
        listProduits.add(baget);
        listProduits.add(baguette);
        listProduits.add(croissant);
        listProduits.add(toast);
        listProduits.add(complet);
        listProduits.add(baget);
        listProduits.add(baguette);
        listProduits.add(croissant);
        listProduits.add(toast);
        listProduits.add(complet);
        listProduits.add(baget);
        listProduits.add(baguette);
        listProduits.add(croissant);
        listProduits.add(toast);
        listProduits.add(complet);
        listProduits.add(baget);
        listProduits.add(baguette);
        listProduits.add(croissant);
        listProduits.add(toast);
        listProduits.add(complet);
        listProduits.add(baget);
        listProduits.add(baguette);
        listProduits.add(croissant);
        listProduits.add(toast);
        listProduits.add(complet);
        listProduits.add(baget);
        listProduits.add(baguette);
        listProduits.add(croissant);
        ///////////////////////////////////////////////////////// BREAD

        Produit emmental = new Produit(R.drawable.emmental, "EMMENTAL", 4.5, 1,
                "Promotion Type", true, 0, "Fromages");
        Produit gouda = new Produit(R.drawable.gouda, "GOUDA", 5.3, 1,
                "Promotion Type", true, 0, "Fromages");
        Produit swiss = new Produit(R.drawable.swiss, "SWISS", 3.5, 1,
                "Promotion Type", true, 0, "Fromages");
        ;
        listProduits.add(emmental);
        listProduits.add(gouda);
        listProduits.add(swiss);
        listProduits.add(emmental);
        listProduits.add(gouda);
        listProduits.add(swiss);
        listProduits.add(emmental);
        listProduits.add(gouda);
        listProduits.add(swiss);
        listProduits.add(emmental);
        listProduits.add(gouda);
        listProduits.add(swiss);
        listProduits.add(emmental);
        listProduits.add(gouda);
        listProduits.add(swiss);
        listProduits.add(emmental);
        listProduits.add(gouda);
        listProduits.add(swiss);
    }
}

