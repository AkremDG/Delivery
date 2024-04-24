package com.example.deliveryboy.View.MissionsFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
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

import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.InternetChecker;
import com.example.deliveryboy.View.PassCommandeActivity;
import com.example.deliveryboy.View.RapportVisite;
import com.example.deliveryboy.ViewModel.MissionsViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Courantes extends Fragment implements RvInterface {

    private boolean isRefreshed=false;
    private List<Mission> missionList = new ArrayList<>();
    private RecyclerView listCmds_Rv;
    private View view;
    private List<Visite> visiteList ;
    private SearchView searchBar_tt;
    private MissionsViewModel missionsViewModel;

    private MaterialToolbar toolbar;
    TabLayout cmds_Tl;

    ProgressBar progressBar;
    MissionsRvAdapter adapter;

    FloatingActionButton addClient_fab;

    ImageView closeIv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_courantes, container, false);
        bindViews();
        uiSetup();
        DisplayData(visiteList);
        listeners();
        //scrollllllllllll show bar searchhhhhh
        //HandleEvents();
        return view;
    }

    private void listeners() {
        addClient_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void uiSetup() {
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.orange_btn_color),
                PorterDuff.Mode.SRC_IN);

        adapter = new MissionsRvAdapter(getContext(), missionList, Courantes.this );
        listCmds_Rv.setLayoutManager(new LinearLayoutManager(getContext()));
        listCmds_Rv.setAdapter(adapter);
    }

    private void HandleEvents() {
        listCmds_Rv.setOnScrollChangeListener(new View.OnScrollChangeListener()     {
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
                        listCmds_Rv.setAdapter(null);
                    }

                }
                return false;
            }

        });


    }

    @SuppressLint("FragmentLiveDataObserve")
    private void DisplayData(List<Visite> visites) {

        missionsViewModel = new MissionsViewModel();
        progressBar.setVisibility(View.VISIBLE);

        if(InternetChecker.isConnected(getContext())){

            missionsViewModel.getMissionsApi(getContext()).observe(Courantes.this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                        getAndDisplayLocalMissions();
                }
            });

        }else {

            getAndDisplayLocalMissions();
        }




    }

    @SuppressLint("FragmentLiveDataObserve")
    private void getAndDisplayLocalMissions() {
        missionsViewModel.getLocalMissions(getContext()).observe(Courantes.this, new Observer<List<Mission>>() {
            @Override
            public void onChanged(List<Mission> missions) {

                if(missions!=null){

                    if(missions.size()>0){

                        List<Mission> resultMissionsFiltration = new ArrayList<>();

                        resultMissionsFiltration =  filterCurrentWeekMissions(missions);

                            progressBar.setVisibility(View.INVISIBLE);
                            missionList.clear();
                            missionList.addAll(resultMissionsFiltration);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);


                    }else {
                        progressBar.setVisibility(View.VISIBLE);

                    }


                }else {
                    progressBar.setVisibility(View.VISIBLE);

                }

            }
        });


    }

    public void bindViews(){
        listCmds_Rv=view.findViewById(R.id.listCmds_Rv);
        searchBar_tt = view.findViewById(R.id.searchBar_tt);
        toolbar = view.findViewById(R.id.toolbar);
        progressBar = view.findViewById(R.id.progressBar);
        cmds_Tl = view.findViewById(R.id.cmds_Tl);
        addClient_fab = view.findViewById(R.id.addClient_fab);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getActivity(), MissionDetails.class);
        startActivity(intent);

    }
    private List<Mission> filterCurrentWeekMissions(List<Mission> missionList) {

        List<Mission> filtredMissionList = new ArrayList<>();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        for(Mission mission : missionList){



            try {
                String startDateStr = mission.getStartOn();
                Date startDate = dateFormat.parse(startDateStr);

                String endDateStr = mission.getEndsOn(); // Corrected to get the end date
                Date endDate = dateFormat.parse(endDateStr);

                String currentDateStr = dateFormat.format(currentDate);
                Date formattedCurrentDate = dateFormat.parse(currentDateStr);

                if (formattedCurrentDate.compareTo(startDate) >= 0 && formattedCurrentDate.compareTo(endDate) <= 0) {
                    Log.i("THIS WEEK", "TRUE"+mission.getStartOn() + " ends in : "+ mission.getEndsOn());

                    filtredMissionList.add(mission);
                } else {
                    Log.i("HORS", "TRUE"+mission.getStartOn() + " ends in : "+ mission.getEndsOn());


                }

            } catch (ParseException e) {
                e.printStackTrace();
            }



        }




        return filtredMissionList;


    }
    public void showAlert(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        closeIv = dialog.findViewById(R.id.close_iv);

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
}