package com.example.deliveryboy.View.DemandeFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.CategoriesAdapter.CatalogClick;
import com.example.deliveryboy.Adapters.CategoriesAdapter.ProductCatalogRv;
import com.example.deliveryboy.Adapters.ProduitRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeProduitInterface;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.ViewModel.ProductsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CreateDemande extends AppCompatActivity implements RvInterface, quantiteInterface, TypeProduitInterface, CatalogClick {
    private Produit produit;
    private MaterialToolbar materialToolbar;
    private EditText searchBar_pass_cmd;
    private Boolean isFilterClicked = false;
    private List<Produit> filtredProducts = new ArrayList<>();
    private String typeProduit;
    private RecyclerView typeCmd_Rv, produids_rv;
    private List<TypeCommande> typeCommandeList;

    private ImageView panier_pass_cmd_Iv;
    private List<Produit> listProduits;

    private List<Produit> selectedProduits = new ArrayList<>();

    AppBarLayout demandeAppBar;

    private ImageView arrow_pass_cmd_Iv;
    private TextView nomClient_Tv;
    private int quantite = 1;
    private User user;
    private int itemCickedPos;
    View view;
    private ProgressBar progressBar;

    ProductsViewModel productsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_commande);

        bindViews();
        DisplayData(listProduits);
        uiListeners();
    }
    public void bindViews() {
        demandeAppBar = findViewById(R.id.demandeAppBar);
       //demandeAppBar.setOutlineProvider(null);
        typeCmd_Rv = findViewById(R.id.typeCmd_Rv);
        produids_rv = findViewById(R.id.produids_rv);
        panier_pass_cmd_Iv =findViewById(R.id.panier_pass_cmd_Iv);
        nomClient_Tv =findViewById(R.id.nomClient_Tv);
        searchBar_pass_cmd =findViewById(R.id.searchBar_pass_cmd);
        arrow_pass_cmd_Iv = findViewById(R.id.arrow_pass_cmd_Iv);
        materialToolbar = findViewById(R.id.demandeCharg_mtb);


        progressBar= findViewById(R.id.progressBar);
        UiUtils.setupProgressBar(getApplicationContext(), progressBar);

    }
    public void DisplayData(List<Produit> listProd) {

        productsViewModel = new ProductsViewModel();



        progressBar.setVisibility(View.VISIBLE);

        productsViewModel.getProductsApi(getApplicationContext()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean==false){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(CreateDemande.this, "ERROR", Toast.LENGTH_SHORT).show();

                    // internetBg.setVisibility(View.VISIBLE);

                    produids_rv.setVisibility(View.VISIBLE);
                  //  getAndDisplayLocalProducts();
                }else {
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(CreateDemande.this, "OKAY", Toast.LENGTH_SHORT).show();

                    produids_rv.setVisibility(View.VISIBLE);
                  //  internetBg.setVisibility(View.GONE);
                  //  getAndDisplayLocalMissions();

                }
            }
        });


        List<String> listCatalogs = new ArrayList<>();
        listCatalogs.add("Bread");

        listCatalogs.add("Jus");
        listCatalogs.add("pates");
        listCatalogs.add("liquides");
        listCatalogs.add("gazeux");
        listCatalogs.add("pain");
        listCatalogs.add("tomates");
        listCatalogs.add("higiéne");
        listCatalogs.add("beauté");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        typeCmd_Rv.setLayoutManager(layoutManager);
        typeCmd_Rv.setAdapter(new ProductCatalogRv(getApplicationContext(),listCatalogs,
                this,typeCmd_Rv));

        /*
        //////////////////////////////////// RV PRODUITS
        produids_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        produids_rv.setAdapter(new ProduitRvAdapter(getApplicationContext(), listProd, this, this));

         */
    }
    public void uiListeners() {

        materialToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDemande.this, BottomNagContainerActivity.class);
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

        /*
        searchBar_pass_cmd.addTextChangedListener(new SearchView.OnQueryTextListener() {
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

         */
    }

    @Override
    public void onItemClick(int position) {
        showAlert(position);
    }

    @Override
    public void onCommanderClick(int position) {

    }

    @Override
    public void onTypeItemClick(int position) {

    }

    @Override
    public void onValidQte(int qte) {

    }

    @Override
    public void onCatalogClick(List<String> catalog) {

    }
    public void showAlert( int pos) {

        final Dialog dialog = new Dialog(CreateDemande.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sheet_desc_produit);

        TextView disponibiliteTv = dialog.findViewById(R.id.disponibilite_Tv);


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



            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}