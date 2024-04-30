package com.example.deliveryboy.View.MissionsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.ClientsRvAdapter;
import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.RegionClick;
import com.example.deliveryboy.Adapters.RegionsRvAdapter;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.Model.Client;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Region;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.ViewModel.MissionsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MissionDetails extends AppCompatActivity implements RegionClick {
    AppBarLayout appBarLayout;
    private RecyclerView typeCmd_Rv,missionsClients_rv;
    EditText searchBar_tt;
    ImageView calIv;
    private Mission mission;
    private List<Client> clientsList;
    private MissionsViewModel missionsViewModel;

    private List<String> regionsList ;
    private TextView dateDebutTv,dateFinTv;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_details);

        getWindow().setStatusBarColor(getResources().getColor(R.color.orange_btn_color));

           Intent intent = getIntent();
           mission = (Mission) getIntent().getSerializableExtra("MissionFromTousFragment");
        regionsList = new ArrayList<>();
        clientsList = new ArrayList<>();
        missionsViewModel = new MissionsViewModel();


        missionsViewModel.getMissionsClients(getApplicationContext(),mission.getMissionId()).observe(MissionDetails.this, new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> clients) {
                if(clients!=null){

                    if(clients.size()>0){

                        setRegionsFilter(clients);
                        clientsList.addAll(clients);
                        setClientsList(clientsList);

                    }
                }

            }
        });

        dateDebutTv = findViewById(R.id.dateTv);
        dateFinTv = findViewById(R.id.dateEndTv);

        dateDebutTv.setText(mission.getStartOn());
        dateFinTv.setText(mission.getEndsOn());


        missionsClients_rv = findViewById(R.id.missionsClients_rv);
        calIv = findViewById(R.id.calIv);
        searchBar_tt = findViewById(R.id.searchBar_tt);

        appBarLayout= findViewById(R.id.detailsAppbar);

        appBarLayout.setOutlineProvider(null);

        typeCmd_Rv = findViewById(R.id.typeCmd_Rv);


        searchBar_tt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Client> filtredList = new ArrayList<>();


                for(Client client : clientsList){
                    if(client.getCT_Intitule().toLowerCase().contains(s) || client.getCT_Intitule().toUpperCase().contains(s)){
                        filtredList.add(client);
                        setClientsList(filtredList);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });








    }

    private void setClientsList(List<Client> clientsList) {

        LinearLayoutManager layoutManagerClients = new LinearLayoutManager(MissionDetails.this, LinearLayoutManager.VERTICAL, false);
        missionsClients_rv.setLayoutManager(layoutManagerClients);
        missionsClients_rv.setAdapter(new ClientsRvAdapter(getApplicationContext(),clientsList));

        HandleEvents();
    }

    private void setRegionsFilter(List<Client> clientsList) {

        for(Client client : clientsList){
            regionsList.add(client.getRegionName());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeCmd_Rv.setLayoutManager(layoutManager);

        typeCmd_Rv.setAdapter(new RegionsRvAdapter(getApplicationContext(),regionsList,this,typeCmd_Rv));


    }

    private void HandleEvents() {
        missionsClients_rv.setOnScrollChangeListener(new View.OnScrollChangeListener()     {
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
                        animateViewVisibilityWithFadeOut(calIv);

                    }
                } else if (scrollY < oldScrollY - scrollThreshold) {
                    // Scrolling up
                    if (scrolledDown) {
                        scrolledDown = false;
                        // Show the views with animation
                        animateViewVisibilityWithFadeIn(searchBar_tt);
                        animateViewVisibilityWithFadeIn(calIv);

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




    }


    @Override
    public void onRegionClick(List<String> position) {


        List<String> regions = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        regions.addAll(position);

        if (position != null) {

            if (position.size() > 0) {

                for (Client client : clientsList) {
                    for (String region : regions) {

                        if (client.getRegionName().equals(region)) {
                            clients.add(client);
                        }

                    }
                }
                setClientsList(clients);

            } else
                setClientsList(clientsList);

        }

    }



}