package com.example.deliveryboy.View.BottomNavFragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.CategoriesAdapter.CatalogClick;
import com.example.deliveryboy.Adapters.CategoriesAdapter.ProductCatalogRv;
import com.example.deliveryboy.Adapters.ProduitRvAdapter;
import com.example.deliveryboy.Adapters.RegionClick;
import com.example.deliveryboy.Adapters.RegionsRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.Adapters.TypeProduitInterface;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.View.PanierActivity;
import com.example.deliveryboy.View.PassCommandeActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DemandeFragment extends Fragment implements RvInterface, quantiteInterface, TypeProduitInterface, CatalogClick {
    private Produit produit;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view =  inflater.inflate(R.layout.fragment_clients, container, false);



        bindViews();
        fakeProductsData();
        DisplayData(listProduits);
        uiListeners();

       return view;
    }
    public void bindViews() {
        demandeAppBar = view.findViewById(R.id.demandeAppBar);
        demandeAppBar.setOutlineProvider(null);

        typeCmd_Rv = view.findViewById(R.id.typeCmd_Rv);
        produids_rv = view.findViewById(R.id.produids_rv);
        panier_pass_cmd_Iv =view. findViewById(R.id.panier_pass_cmd_Iv);
        nomClient_Tv =view. findViewById(R.id.nomClient_Tv);
        searchBar_pass_cmd = view.findViewById(R.id.searchBar_pass_cmd);
        arrow_pass_cmd_Iv = view.findViewById(R.id.arrow_pass_cmd_Iv);
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
    public void DisplayData(List<Produit> listProd) {



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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        typeCmd_Rv.setLayoutManager(layoutManager);
        typeCmd_Rv.setAdapter(new ProductCatalogRv(getContext(),listCatalogs,this,typeCmd_Rv));

        //////////////////////////////////// RV PRODUITS
        produids_rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        produids_rv.setAdapter(new ProduitRvAdapter(getContext(), listProd, this, this));
    }
    public void uiListeners() {


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

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sheet_desc_produit);

        TextView disponibiliteTv = dialog.findViewById(R.id.disponibilite_Tv);

        if (listProduits.get(pos).getDispProduit() == false) {
            disponibiliteTv.setText(String.valueOf("epuisé"));
            disponibiliteTv.setBackgroundColor(getResources().getColor(R.color.error_red));
        }

        TextView nomProduit = dialog.findViewById(R.id.nomProduit_Tv);
        nomProduit.setText(listProduits.get(pos).getNomProduit());

        ImageView closeSheetBtn = dialog.findViewById(R.id.close_descProduit_iv);
        closeSheetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView typePromotion = dialog.findViewById(R.id.promo_val_Tv);
        typePromotion.setText(listProduits.get(pos).getTypePromotion());


        ImageView imageProduit = dialog.findViewById(R.id.produit_Iv);
        imageProduit.setImageResource(listProduits.get(pos).getImageProduit());

        TextView prixUnitaire = dialog.findViewById(R.id.prix_val_Tv);
        prixUnitaire.setText(String.valueOf(listProduits.get(pos).getPrixProduit()) + " dt");


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

                    double totl = quantite * listProduits.get(pos).getPrixProduit();

                    produit = new Produit(listProduits.get(pos).getImageProduit(),
                            listProduits.get(pos).getNomProduit(),
                            listProduits.get(pos).getPrixProduit(),
                            quantite,
                            listProduits.get(pos).getTypePromotion(),
                            listProduits.get(pos).getDispProduit(), totl, listProduits.get(pos).getCategorie());

                    selectedProduits.add(produit);

                    UiUtils.showSnackbar(view,"Article ajouté","Fermer");

                } catch (Exception e) {
                    UiUtils.showSnackbar(view,"Article ajouté","Erreur");
                }

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}