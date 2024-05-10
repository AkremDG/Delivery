package com.example.deliveryboy.View.DemandeFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deliveryboy.Adapters.CategoriesAdapter.CatalogClick;
import com.example.deliveryboy.Adapters.CategoriesAdapter.ProductCatalogRv;
import com.example.deliveryboy.Adapters.ProduitRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeProduitInterface;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.CustomProduit;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.InternetChecker;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateDemande extends AppCompatActivity implements RvInterface, quantiteInterface, TypeProduitInterface, CatalogClick {
    private MaterialToolbar materialToolbar;
    ProduitRvAdapter productsAdapter;
    private EditText searchBar_pass_cmd;
    private List<Produit> filtredProducts = new ArrayList<>();
    private String typeProduit;
    private RecyclerView typeCmd_Rv, produids_rv;
    private List<TypeCommande> typeCommandeList;

    private FloatingActionButton synchro_fab;
    private ImageView panier_pass_cmd_Iv;
    private List<Produit> listProduits = new ArrayList<>();

    private List<Produit> selectedProduits = new ArrayList<>();

    AppBarLayout demandeAppBar;

    private ImageView arrow_pass_cmd_Iv;
    private TextView nomClient_Tv;
    private int quantite = 1;
    private User user;
    private int itemCickedPos;
    ConstraintLayout view;
    private ProgressBar progressBar;
    private ConstraintLayout internetBg;

    DemandeChargViewModel demandeChargViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_commande);


        bindViews();
        DisplayData(listProduits);
        uiSetup();
        uiListeners();
    }
    public void bindViews() {
        view = findViewById(R.id.global_constraint);
        synchro_fab = findViewById(R.id.synchro_fab);
        demandeAppBar = findViewById(R.id.demandeAppBar);
        internetBg = findViewById(R.id.internetBg);
       //demandeAppBar.setOutlineProvider(null);
        typeCmd_Rv = findViewById(R.id.typeCmd_Rv);
        produids_rv = findViewById(R.id.produids_rv);
        panier_pass_cmd_Iv =findViewById(R.id.panier_pass_cmd_Iv);
        nomClient_Tv =findViewById(R.id.nomClient_Tv);
        searchBar_pass_cmd =findViewById(R.id.searchBar_pass_cmd);
        arrow_pass_cmd_Iv = findViewById(R.id.arrow_pass_cmd_Iv);
        materialToolbar = findViewById(R.id.demandeCharg_mtb);

        searchBar_pass_cmd = findViewById(R.id.searchBar_pass_cmd);
        progressBar= findViewById(R.id.progressBar);
        UiUtils.setupProgressBar(getApplicationContext(), progressBar);

    }
    public void DisplayData(List<Produit> listProd) {

        demandeChargViewModel = new DemandeChargViewModel();



        progressBar.setVisibility(View.VISIBLE);

        demandeChargViewModel.getProductsApi(getApplicationContext()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean==false){
                    internetBg.setVisibility(View.VISIBLE);
                    getAndDisplayLocalProducts();

                }else {
                    internetBg.setVisibility(View.GONE);

                    getAndDisplayLocalProducts();

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

    }

    private void getAndDisplayLocalProducts() {
        demandeChargViewModel.getLocalProducts(getApplicationContext()).observe(this, new Observer<List<Produit>>() {
            @Override
            public void onChanged(List<Produit> produits) {
                if(produits !=null){
                    if(produits.size()>0){

                        progressBar.setVisibility(View.INVISIBLE);

                        listProduits.clear();
                        listProduits.addAll(produits);
                        productsAdapter.notifyDataSetChanged();


                    }
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.i("LOCALLLLLPRODUIT", "ERRORRRRRRRRRR");

                }
            }
        });
    }

    public void uiListeners() {
        synchro_fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("FragmentLiveDataObserve")
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                produids_rv.setVisibility(View.GONE);


                if(InternetChecker.isConnectedToInternet(getApplicationContext())){

                    demandeChargViewModel.getProductsApi(getApplicationContext()).observe(CreateDemande.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean==false){
                                UiUtils.showSnackbar(view,"Erreur synchronisation","Fermer");
                                internetBg.setVisibility(View.VISIBLE);

                                produids_rv.setVisibility(View.VISIBLE);
                                getAndDisplayLocalProducts();
                            }else {
                                internetBg.setVisibility(View.GONE);
                                 produids_rv.setVisibility(View.VISIBLE);

                                getAndDisplayLocalProducts();

                            }
                        }
                    });

                }else {
                    progressBar.setVisibility(View.GONE);
                    produids_rv.setVisibility(View.VISIBLE);

                    internetBg.setVisibility(View.VISIBLE);

                    UiUtils.showSnackbar(view,"Erreur de connexion internet","Fermer");
                }

            }
        });
        searchBar_pass_cmd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Produit> filtredProductsList = new ArrayList<>();

                for(Produit product : listProduits){
                    if(product.getAR_Design().toLowerCase().contains(s) || product.getAR_Design().toUpperCase(Locale.ROOT).contains(s) ){
                        filtredProductsList.add(product);
                        setProductsList(filtredProductsList);
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDemande.this, BottomNagContainerActivity.class);
                startActivity(intent);
            }
        });



        produids_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private static final int SCROLL_THRESHOLD = 20;
            private boolean scrolledDown = false;
            private int oldScrollY = 0;
            private boolean isAnimating = false;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int scrollY = recyclerView.computeVerticalScrollOffset();

                if (scrollY > oldScrollY + SCROLL_THRESHOLD && !isAnimating) {
                    // Scrolling down
                    if (!scrolledDown) {
                        scrolledDown = true;
                        isAnimating = true;
                        // Hide the views with animation
                        animateViewVisibilityWithFadeOut(searchBar_pass_cmd, () -> isAnimating = false);
                        animateViewVisibilityWithFadeOut(typeCmd_Rv, null);
                    }
                } else if (scrollY < oldScrollY - SCROLL_THRESHOLD && !isAnimating) {
                    // Scrolling up
                    if (scrolledDown) {
                        scrolledDown = false;
                        isAnimating = true;
                        // Show the views with animation
                        animateViewVisibilityWithFadeIn(searchBar_pass_cmd, null);
                        animateViewVisibilityWithFadeIn(typeCmd_Rv, () -> isAnimating = false);
                    }
                }

                oldScrollY = scrollY;
            }

            private void animateViewVisibilityWithFadeOut(final View view, Runnable onAnimationEnd) {
                view.animate()
                        .alpha(0f)
                        .withEndAction(() -> {
                            view.setVisibility(View.GONE);
                            if (onAnimationEnd != null) onAnimationEnd.run();
                        })
                        .start();
            }

            private void animateViewVisibilityWithFadeIn(final View view, Runnable onAnimationEnd) {
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0f);
                view.animate()
                        .alpha(1f)
                        .withEndAction(() -> {
                            if (onAnimationEnd != null) onAnimationEnd.run();
                        })
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
    }

    @Override
    public void onCommanderClick(int position) {

    }

    @Override
    public void onItemClickReturnObject(CustomProduit customProduit, int position) {
        showAlert(customProduit, position);

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
    public void showAlert(CustomProduit customProduit, int position) {

        final Dialog dialog = new Dialog(CreateDemande.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sheet_desc_produit);



        TextView prixTv = dialog.findViewById(R.id.prix_val_Tv);
        prixTv.setText(String.valueOf(customProduit.getProductPrice()));

        TextView productName = dialog.findViewById(R.id.nomProduit_Tv);
        productName.setText(String.valueOf(customProduit.getProductName()));

        TextView refTv = dialog.findViewById(R.id.promo_val_Tv);
        refTv.setText(String.valueOf(customProduit.getProductRef()+ " "+customProduit.getProductCondition()));
        List<String> stringList = new ArrayList<>();


        Spinner conditionSpinner = dialog.findViewById(R.id.conditionSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateDemande.this, R.layout.customspinner,R.id.regionName_tv, stringList);
        stringList.add(customProduit.getProductCondition());
        conditionSpinner.setAdapter(adapter);


        /*
        List<ProduitCondition> produitConditionList = new ArrayList<>();
        demandeChargViewModel.getLocalProductsConditions(getApplicationContext(), listProduits.get(position).getBoId()).observe(this, new Observer<List<ProduitCondition>>() {
            @Override
            public void onChanged(List<ProduitCondition> produitConditions) {
                for(ProduitCondition produitCondition : produitConditions){

                    if(!produitConditionList.contains(produitCondition.getEC_Enumere())){
                        stringList.add(produitCondition.getEC_Enumere());
                    }
                }
            }

        });

         */



        ImageView plus = dialog.findViewById(R.id.plus_Iv);
        ImageView moins = dialog.findViewById(R.id.moins_Iv);


        TextView qte_Tv = dialog.findViewById(R.id.qte_Tv);
        qte_Tv.setText(String.valueOf(quantite));


        ImageView closeIv = dialog.findViewById(R.id.close_descProduit_iv);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


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
    private void uiSetup() {

         productsAdapter = new ProduitRvAdapter(this,this, listProduits, this, this);
        produids_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        produids_rv.setAdapter(productsAdapter);

    }

    private void setProductsList(List<Produit> productsList) {

        productsAdapter = new ProduitRvAdapter(this,this, productsList, this, this);
        produids_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        produids_rv.setAdapter(productsAdapter);

    }
}