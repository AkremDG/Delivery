package com.example.deliveryboy.View.DemandeFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.CategoriesAdapter.CatalogClick;
import com.example.deliveryboy.Adapters.CategoriesAdapter.ProductCatalogRv;
import com.example.deliveryboy.Adapters.ProduitRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeProduitInterface;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.CustomProduit;
import com.example.deliveryboy.Model.Demande;
import com.example.deliveryboy.Model.DemandeProduitItem;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.SelectedProduit;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.InternetChecker;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.View.PanierActivity;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateDemande extends AppCompatActivity implements RvInterface, quantiteInterface, TypeProduitInterface, CatalogClick {
    private MaterialToolbar materialToolbar;
    int id =0;
    private List<ProduitCondition> selectedProduitsConditionArticleX = new ArrayList<>();
    List<SelectedProduit> selectedProducts = new ArrayList<>();

    float x1,y1,x2,y2;

    private ProduitRvAdapter productsAdapter;
    private Double actualStock=0.0;
    private EditText searchBar_pass_cmd;
    private List<Produit> filtredProducts = new ArrayList<>();
    private String typeProduit;
    private RecyclerView typeCmd_Rv, produids_rv;
    private List<TypeCommande> typeCommandeList;
    String selectedConditionSpinner ;

    private FloatingActionButton synchro_fab;
    private ImageView panier_pass_cmd_Iv;
    private List<Produit> listProduits = new ArrayList<>();

    private List<Produit> selectedProduits = new ArrayList<>();

    AppBarLayout demandeAppBar;

    private ImageView arrow_pass_cmd_Iv;
    private TextView nomClient_Tv;
    private int quantite = 1;
    private Double totalProductPrice;
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
                        produids_rv.setVisibility(View.VISIBLE);

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

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDemande.this, BottomNagContainerActivity.class);
                startActivity(intent);
            }
        });

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Check the ID of the clicked MenuItem
                switch (item.getItemId()) {
                    case R.id.panier:
                    {
                        Intent intent = new Intent(CreateDemande.this, PanierActivity.class);
                        intent.putExtra("selectedItems",(Serializable) selectedProducts);
                        startActivity(intent);
                    }
                }
                return false; // Return false for items that are not handled here
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

        int positionRv = position;


        final Dialog dialog = new Dialog(CreateDemande.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sheet_desc_produit);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                quantite=1;
            }
        });

        MaterialButton addButton = dialog.findViewById(R.id.ajout_btn);

        TextView refTv = dialog.findViewById(R.id.promo_val_Tv);
        refTv.setText(String.valueOf(customProduit.getProductRef()));

        TextView errorQteTv = dialog.findViewById(R.id.errorQteTv);


        TextView conditionn_Tv = dialog.findViewById(R.id.conditionn_Tv);
        conditionn_Tv.setText(String.valueOf("Réference : "+listProduits.get(positionRv).getAR_Ref()));



        TextView qte_Tv = dialog.findViewById(R.id.qte_Tv);
        qte_Tv.setText(String.valueOf(quantite));



        ImageView plus = dialog.findViewById(R.id.plus_Iv);
        ImageView moins = dialog.findViewById(R.id.moins_Iv);


        TextView prixTv = dialog.findViewById(R.id.prix_val_Tv);
        prixTv.setText(String.valueOf(customProduit.getProductPrice()));

        TextView prixTotTvVal = dialog.findViewById(R.id.prixTotTvVal);
        prixTotTvVal.setText(String.valueOf(customProduit.getProductPrice()));


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantite++;

                int stock = (int) Math.floor(actualStock);

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

        ///////SPINERRRRRRRRRR
        List<String> conditionsStrings = new ArrayList<>();

        Spinner conditionSpinner = dialog.findViewById(R.id.conditionSpinner);
        conditionsStrings.add(customProduit.getProductCondition());

        demandeChargViewModel.getLocalProductsConditions(CreateDemande.this,listProduits.get(position).getBoId()).observe(CreateDemande.this, new Observer<List<ProduitCondition>>() {
            @Override
            public void onChanged(List<ProduitCondition> produitConditions) {
                selectedProduitsConditionArticleX.addAll(produitConditions);
                Log.i("ConditionnementProduit", produitConditions.toString());
                for(ProduitCondition produitCondition : produitConditions){
                    if(!conditionsStrings.contains(produitCondition.getEC_Enumere())){
                        conditionsStrings.add(produitCondition.getEC_Enumere());
                    }
                }
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateDemande.this, R.layout.customspinner,R.id.regionName_tv, conditionsStrings);
        conditionSpinner.setAdapter(adapter);




        conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                demandeChargViewModel.getLocalPriceByIdAndProductId(CreateDemande.this,
                        listProduits.get(positionRv).getBoId(),
                        String.valueOf(parent.getItemAtPosition(position))).observe(CreateDemande.this,
                        new Observer<ProduitCondition>() {
                            @Override
                            public void onChanged(ProduitCondition produitCondition) {

                                if(produitCondition!=null){

                                    quantite = Integer.valueOf(qte_Tv.getText().toString());
                            /*
                            if(!qte_Tv.getText().toString().equals("1") ){
                                qte_Tv.setText("1");
                            }

                             */

                                    totalProductPrice = quantite * produitCondition.getTC_Prix();
                                    prixTotTvVal.setText(String.valueOf(totalProductPrice));

                                    prixTv.setText(String.valueOf(produitCondition.getTC_Prix()));


                                    refTv.setText(String.valueOf(produitCondition.getAS_QteSto()));
                                    actualStock = produitCondition.getAS_QteSto();

                                    if(produitCondition.getAS_QteSto()==0){
                                        addButton.setBackgroundColor(Color.parseColor("#FEF0DD"));

                                        refTv.setTextColor(Color.RED);
                                        addButton.setEnabled(false);

                                    }else {
                                        refTv.setTextColor(Color.parseColor("#49968C"));
                                        addButton.setEnabled(true);
                                        addButton.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.orange_btn_color));

                                    }
                                }

                            }
                        });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //////////////////////
        qte_Tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                try {

                    String val = String.valueOf(s);
                    quantite = Integer.valueOf(val);

                    int stock = (int) Math.floor(actualStock);

                    if(quantite>stock){



                        quantite = (int) Math.floor(actualStock);
                        qte_Tv.setText(String.valueOf(quantite));
                        qte_Tv.setTextColor(getResources().getColor(R.color.orange_btn_color));
                        errorQteTv.setVisibility(View.VISIBLE);

                        //quantite = actualStock;
                        qte_Tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                qte_Tv.setText("");
                                quantite = 1;

                            }
                        });


                    }else if(quantite<=0){
                        quantite = 1;
                        qte_Tv.setText("1");

                    }
                    else if(quantite<=stock){
                        qte_Tv.setTextColor(Color.parseColor("#FFA5A5A5"));
                        errorQteTv.setVisibility(View.GONE);

                    }
                    String stringUnitPrice =   prixTv.getText().toString();
                    stringUnitPrice = stringUnitPrice.replace(",", ".");

                    totalProductPrice = quantite * Double.valueOf(stringUnitPrice);

                    DecimalFormat df = new DecimalFormat("#.###");
                    String formattedTotalPrice = df.format(totalProductPrice);
                    formattedTotalPrice = formattedTotalPrice.replace(",", ".");

                   // prixTotTvVal.setText(String.valueOf(String.valueOf(totalProductPrice)));
                    prixTotTvVal.setText(String.valueOf(String.valueOf(formattedTotalPrice)));

                }catch (Exception e){

                }


            }



            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().equals("")){
                    errorQteTv.setVisibility(View.VISIBLE);
                    errorQteTv.setText("Choisir la quantitée !");
                    conditionSpinner.setVisibility(View.GONE);
                    addButton.setEnabled(false);

                }else {
                    errorQteTv.setVisibility(View.GONE);
                    errorQteTv.setText("Stock indisponible !");
                    conditionSpinner.setVisibility(View.VISIBLE);
                    addButton.setEnabled(true);

                }
            }
        });






        TextView productName = dialog.findViewById(R.id.nomProduit_Tv);
        productName.setText(String.valueOf(customProduit.getProductName()));


        ImageView closeIv = dialog.findViewById(R.id.close_descProduit_iv);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                quantite =1;

            }
        });





        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String selectedondition =  conditionSpinner.getSelectedItem().toString();
                String stringUnitPrice =   prixTv.getText().toString();
                String.valueOf(refTv.getText().toString());
                Double selectedStock = Double.valueOf(refTv.getText().toString()) ;
                 String selectedQuantity =    qte_Tv.getText().toString();
                String totalPrice =  prixTotTvVal.getText().toString();

                SelectedProduit selectedProduit = new SelectedProduit(

                            selectedondition,
                            listProduits.get(position).getLocalArticleId(),
                            Double.parseDouble(customProduit.getProductPrice()),
                            Double.parseDouble(stringUnitPrice),
                            Integer.valueOf(selectedQuantity),

                            listProduits.get(position).getBoId(),
                            listProduits.get(position).getAR_Ref(),
                            listProduits.get(position).getAR_Design(),
                            selectedProduitsConditionArticleX,
                            conditionsStrings,
                            selectedStock,
                            Double.valueOf(totalPrice),
                            null

                );

                    selectedProducts.add(selectedProduit);
                    // Animate the icon moving up
                    ImageView basketIcon = dialog.findViewById(R.id.produit_Iv);

                    ObjectAnimator translateY = ObjectAnimator.ofFloat(basketIcon, "translationY", 0, -200);
                    translateY.setDuration(500); // Adjust duration as needed
                    translateY.start();

                    // Animate the icon fading out
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(basketIcon, View.ALPHA, 1.0f, 0.0f);
                    alpha.setDuration(500); // Adjust duration as needed
                    alpha.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            // Remove the icon from the dialog after animation ends
                            ViewGroup parent = (ViewGroup) basketIcon.getParent();
                            if (parent != null) {
                                parent.removeView(basketIcon);
                                dialog.dismiss();
                                quantite =1;
                            }
                        }
                    });
                    alpha.start();


                    materialToolbar.getMenu().getItem(0).setIcon(getResources().getDrawable(R.drawable.filled_red_panier_icon));




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