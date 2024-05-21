package com.example.deliveryboy.View.DemandeFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.DemandesAdapter.DemandesRvAdapter;
import com.example.deliveryboy.Model.CustomProduit;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.InternetChecker;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ValidDemandsFragment extends Fragment {
    private boolean isSynchronized = true ;
    private ConstraintLayout internetBg;

    private View constraintRootView;

    private List<GETDemandeChargementRes> demandesList = new ArrayList<>();
    private RecyclerView listDemandsRv;
    private View view;
    private List<Visite> visiteList ;
    private SearchView searchBar_tt;
    private DemandeChargViewModel demandeChargViewModel;
    private TextView synchTv;



    private ProgressBar progressBar;
    private DemandesRvAdapter adapter;

    private FloatingActionButton addDemandFab,synchro_fab,dateFab;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_valid_demands, container, false);

        bindViews();
        uiSetup(demandesList);
        DisplayData();
        uiListeners();


        return view;

    }

    private void uiListeners() {

        addDemandFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateDemande.class);
                startActivity(intent);
            }
        });



        synchro_fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("FragmentLiveDataObserve")
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                listDemandsRv.setVisibility(View.GONE);


                if(InternetChecker.isConnectedToInternet(getContext())){

                    demandeChargViewModel.getDemandesApi(getContext()).observe(ValidDemandsFragment.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean==false){
                                UiUtils.showSnackbar(view,"Erreur synchronisation","Fermer");

                                internetBg.setVisibility(View.VISIBLE);
                                isSynchronized = false;

                                listDemandsRv.setVisibility(View.VISIBLE);
                                getAndDisplayLocalMissions();
                            }else {
                                listDemandsRv.setVisibility(View.VISIBLE);
                                internetBg.setVisibility(View.GONE);
                                isSynchronized = true;

                                getAndDisplayLocalMissions();

                            }
                        }
                    });

                }else {
                    progressBar.setVisibility(View.GONE);
                    listDemandsRv.setVisibility(View.VISIBLE);

                    internetBg.setVisibility(View.VISIBLE);
                    isSynchronized = false;

                    UiUtils.showSnackbar(view,"Erreur de connexion internet","Fermer");
                }

            }
        });


        dateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<GETDemandeChargementRes> filtredListByDate = new ArrayList<>();

                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Choisir la date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();


                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(selection));

                        Log.i("DATEEEEEEEEE", date);



                        for(GETDemandeChargementRes demandeChargementRes : demandesList){
                            if(demandeChargementRes.getDO_Date().equals(date)){

                                filtredListByDate.add(demandeChargementRes);



                            }
                        }


                        if(filtredListByDate.size()>0){

                            uiSetup(filtredListByDate);
                            synchTv.setText(date);

                            internetBg.setVisibility(View.VISIBLE);
                            // dont seeeeeeeeeeeeet

                        }
                    }

                });

                materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uiSetup(demandesList);
                        synchTv.setText("Données non synchnorisés");

                        if(isSynchronized == true){
                            internetBg.setVisibility(View.GONE);
                        }else {
                            internetBg.setVisibility(View.VISIBLE);

                        }

                    }
                });
                materialDatePicker.show(getChildFragmentManager(), "tag");

            }
        });


        listDemandsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scrolling down
                    dateFab.hide();
                    synchro_fab.hide();
                    addDemandFab.hide();
                } else if (dy < 0) {
                    // Scrolling up
                    dateFab.show();
                    synchro_fab.show();
                    addDemandFab.show();
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Scrolling stopped
                    dateFab.show();
                    synchro_fab.show();
                    addDemandFab.show();


                }
            }
        });




    }
    private void uiSetup(List<GETDemandeChargementRes> filtredList) {
        demandeChargViewModel = new DemandeChargViewModel();


        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.orange_btn_color),
                PorterDuff.Mode.SRC_IN);

        adapter = new DemandesRvAdapter(getContext(), filtredList );
        listDemandsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        listDemandsRv.setAdapter(adapter);
    }


    @SuppressLint("FragmentLiveDataObserve")
    private void DisplayData() {


        if(InternetChecker.isConnectedToInternet(getContext())){

            demandeChargViewModel.getDemandesApi(getContext()).observe(ValidDemandsFragment.this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean==false){
                        internetBg.setVisibility(View.VISIBLE);
                        isSynchronized = false;

                        getAndDisplayLocalMissions();
                    }else {
                        internetBg.setVisibility(View.GONE);
                        isSynchronized = true;

                        getAndDisplayLocalMissions();
                    }
                }
            });

        }else {

            progressBar.setVisibility(View.GONE);

            internetBg.setVisibility(View.VISIBLE);
            isSynchronized = false;

            getAndDisplayLocalMissions();
        }




    }
    @SuppressLint("FragmentLiveDataObserve")
    private void getAndDisplayLocalMissions() {
        demandeChargViewModel.getLocalCloturesDemandes(getContext()).observe(ValidDemandsFragment.this, new Observer<List<GETDemandeChargementRes>>() {
            @Override
            public void onChanged(List<GETDemandeChargementRes> demandeChargementResList) {
                if(demandeChargementResList!=null){

                    if(demandeChargementResList.size()>0){

                        progressBar.setVisibility(View.GONE);

                        progressBar.setVisibility(View.INVISIBLE);
                        demandesList.clear();

                        demandesList.addAll(demandeChargementResList);

                        adapter.notifyDataSetChanged();

                    }else {

                        progressBar.setVisibility(View.VISIBLE);

                    }


                }

            }
        });
    }


    public void bindViews(){

        internetBg = view.findViewById(R.id.internetBg);

        synchTv = view.findViewById(R.id.synchTv);

        listDemandsRv=view.findViewById(R.id.listDemands_Rv);

        searchBar_tt = view.findViewById(R.id.searchBar_tt);


        progressBar = view.findViewById(R.id.demandsProgressBar);

        addDemandFab = view.findViewById(R.id.add_demand_fab);
        synchro_fab = view.findViewById(R.id.synchro_demandes_fab);

        dateFab = view.findViewById(R.id.dateFab);

    }






}