package com.example.deliveryboy.View.DemandeFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.DemandesAdapter.DemandesRvAdapter;
import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Model.CustomProduit;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.InternetChecker;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.MissionsFragment.MissionDetails;
import com.example.deliveryboy.View.MissionsFragment.TousFragment;
import com.example.deliveryboy.ViewModel.DemandeChargViewModel;
import com.example.deliveryboy.ViewModel.MissionsViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.http.GET;


public class TousDemandesFragment extends Fragment implements RvInterface {

    private ConstraintLayout internetBg;

    private List<GETDemandeChargementRes> demandesList = new ArrayList<>();
    private RecyclerView listDemandsRv;
    private View view;
    private List<Visite> visiteList ;
    private SearchView searchBar_tt;
    private DemandeChargViewModel demandeChargViewModel;
    private TextView synchTv;



    private ProgressBar progressBar;
    private DemandesRvAdapter adapter;

    private FloatingActionButton addDemandFab,synchro_fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tous_demandes, container, false);

        bindViews();
        uiSetup();
        DisplayData(visiteList);
        uiListeners();
        //scrollllllllllll show bar searchhhhhh
        //HandleEvents();

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

                //.setVisibility(View.VISIBLE);
                //listDemandsRv.setVisibility(View.GONE);


                if(InternetChecker.isConnectedToInternet(getContext())){



                }else {
                    //progressBar.setVisibility(View.GONE);
                    //listDemandsRv.setVisibility(View.VISIBLE);

                    //internetBg.setVisibility(View.VISIBLE);

                    //UiUtils.showSnackbar(view,"Erreur de connexion internet","Fermer");
                }

            }
        });
    }
    private void uiSetup() {
        demandeChargViewModel = new DemandeChargViewModel();


        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.orange_btn_color),
                PorterDuff.Mode.SRC_IN);

        adapter = new DemandesRvAdapter(getContext(), demandesList );
        listDemandsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        listDemandsRv.setAdapter(adapter);
    }

    private void HandleEvents() {
        listDemandsRv.setOnScrollChangeListener(new View.OnScrollChangeListener()     {
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
                        animateViewVisibilityWithFadeOut(searchBar_tt);
                    }
                } else if (scrollY < oldScrollY - scrollThreshold) {
                    // Scrolling up
                    if (scrolledDown) {
                        scrolledDown = false;
                        // Show the views with animation
                        animateViewVisibilityWithFadeIn(searchBar_tt);
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


        searchBar_tt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Visite> listVisists = new ArrayList<>();
                boolean dataFound = false;

                if (newText.length()==0) {
                    DisplayData(visiteList);
                }
                else{

                    for (Visite visite : visiteList) {
                        if (visite.getTypeVisite().toLowerCase(Locale.ROOT).contains(newText) || visite.getZone().toLowerCase().contains(newText) || visite.getUser().getEmail().toLowerCase(Locale.ROOT).contains(newText)) {
                            listVisists.add(visite);
                            dataFound = true;
                        }
                    }
                    if (dataFound) {
                        DisplayData(listVisists);
                    } else {
                        listDemandsRv.setAdapter(null);
                    }

                }
                return false;
            }

        });


    }

    private void DisplayData(List<Visite> visites) {


        if(InternetChecker.isConnectedToInternet(getContext())){
            progressBar.setVisibility(View.VISIBLE);

            demandeChargViewModel.getDemandesApi(getContext()).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean==false){
                        internetBg.setVisibility(View.VISIBLE);
                        listDemandsRv.setVisibility(View.VISIBLE);
                        getAndDisplayLocalMissions();
                    }else {
                        listDemandsRv.setVisibility(View.VISIBLE);
                        internetBg.setVisibility(View.GONE);

                        getAndDisplayLocalMissions();

                    }
                }
            });



        }else {

            UiUtils.showSnackbar(view,"Pas de connexion internet ","Fermer");
        }




    }
    @SuppressLint("FragmentLiveDataObserve")
    private void getAndDisplayLocalMissions() {
        demandeChargViewModel.getLocalDemandes(getContext()).observe(TousDemandesFragment.this, new Observer<List<GETDemandeChargementRes>>() {
            @Override
            public void onChanged(List<GETDemandeChargementRes> demandeChargementResList) {
                if(demandeChargementResList!=null){

                    if(demandeChargementResList.size()>0){

                        Log.i("LOCALLLLLL", demandeChargementResList.get(0).toString());

                        progressBar.setVisibility(View.INVISIBLE);
                        demandesList.clear();
                        demandesList.addAll(demandeChargementResList);
                        adapter.notifyDataSetChanged();





                    }else {
                        Log.i("LOCALLLLLL", "null");

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
    }

    @Override
    public void onItemClick(int position) {

        /*
        Intent intent = new Intent(getActivity(), MissionDetails.class);
        intent.putExtra("MissionIntent",(Serializable) missionList.get(position));
        startActivity(intent);

         */


    }

    @Override
    public void onCommanderClick(int position) {

    }

    @Override
    public void onItemClickReturnObject(CustomProduit customProduit, int position) {

    }

    public void showAlert(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        ImageView closeIv = dialog.findViewById(R.id.close_iv);

        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


    }

    public void fakeMissionsData(){
        visiteList = new ArrayList<>();


    }
}