package com.example.deliveryboy.View.MissionsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.Model.Client;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Region;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.ViewModel.MissionsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MissionDetails extends AppCompatActivity implements RegionClick, RvInterface {
    private AppBarLayout appBarLayout;
    MaterialToolbar detailsMissions_mt;

    private ConstraintLayout constraint_date_end,constraint_date_start;
    private RecyclerView typeCmd_Rv,missionsClients_rv;
    private EditText searchBar_tt;
    private ImageView calIv;

    private Mission mission;
    private List<Client> clientsList;
    private MissionsViewModel missionsViewModel;

    private List<String> regionsList ;
    private TextView dateDebutTv,dateFinTv,dayTv,dayEndTv,startDateIv,endDatetextTv,clientAVisiterTv;


    @SuppressLint({"NewApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_details);

        getWindow().setStatusBarColor(getResources().getColor(R.color.orange_btn_color));

        mission = (Mission) getIntent().getSerializableExtra("MissionIntent");
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
        dayTv = findViewById(R.id.dayTv);
        dayEndTv = findViewById(R.id.dayEndTv);
        clientAVisiterTv = findViewById(R.id.clientListTv);


        startDateIv = findViewById(R.id.startDateIv);
        endDatetextTv = findViewById(R.id.endDatetextTv);


        constraint_date_end = findViewById(R.id.constraint_date_end);
        constraint_date_start = findViewById(R.id.constraint_date);

        dateDebutTv.setText(mission.getStartOn());
        dateFinTv.setText(mission.getEndsOn());


        String day = getDayConversion(mission.getStartOn());
        String dayeND = getDayConversion(mission.getEndsOn());


        dayTv.setText(day);
        dayEndTv.setText(dayeND);



        missionsClients_rv = findViewById(R.id.missionsClients_rv);
        calIv = findViewById(R.id.calIv);
        searchBar_tt = findViewById(R.id.searchBar_tt);

        appBarLayout= findViewById(R.id.detailsAppbar);
        detailsMissions_mt = findViewById(R.id.detailsMissions_mt);
        appBarLayout.setOutlineProvider(null);

        typeCmd_Rv = findViewById(R.id.typeCmd_Rv);

        detailsMissions_mt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MissionDetails.this, BottomNagContainerActivity.class);
                startActivity(intent);
            }
        });

        HandleEvents();


    }

    private void setClientsList(List<Client> clientsList) {

        LinearLayoutManager layoutManagerClients = new LinearLayoutManager(MissionDetails.this, LinearLayoutManager.VERTICAL, false);
        missionsClients_rv.setLayoutManager(layoutManagerClients);
        missionsClients_rv.setAdapter(new ClientsRvAdapter(getApplicationContext(),clientsList,this));

    }

    private void setRegionsFilter(List<Client> clientsList) {



        for (Client client : clientsList) {
            String regionName = client.getRegionName();
            if (!regionsList.contains(regionName)) {
                regionsList.add(regionName);
            }
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

                        /*
                        animateViewVisibilityWithFadeOut(constraint_date_end);
                        animateViewVisibilityWithFadeOut(constraint_date_start);

                        animateViewVisibilityWithFadeOut(startDateIv);
                        animateViewVisibilityWithFadeOut(endDatetextTv);

                         */


                        //clientAvisiter
                        animateViewVisibilityWithFadeOut(clientAVisiterTv);

                    }
                } else if (scrollY < oldScrollY - scrollThreshold) {
                    // Scrolling up
                    if (scrolledDown) {
                        scrolledDown = false;
                        // Show the views with animation
                        animateViewVisibilityWithFadeIn(searchBar_tt);

                        animateViewVisibilityWithFadeIn(constraint_date_end);
                        animateViewVisibilityWithFadeIn(constraint_date_start);

                        animateViewVisibilityWithFadeIn(startDateIv);
                        animateViewVisibilityWithFadeIn(endDatetextTv);


                        //clientAvisiter
                        animateViewVisibilityWithFadeIn(clientAVisiterTv);


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

    public String getDayConversion(String dateString){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dayName="";

        try {
            Date date = dateFormat.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

             dayName = getDayName(dayOfWeek);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayName;
    }
    private String getDayName(int dayOfWeek) {

        String[] dayNames = new String[] {"Dimanche","Lundi","tuesday","wednesday","thursday","friday","saturday"};
        return dayNames[dayOfWeek - 1];
    }

    @Override
    public void onItemClick(int position) {

        showAlert(position);
    }

    @Override
    public void onCommanderClick(int position) {
        Toast.makeText(this, clientsList.get(position).getCT_Intitule(), Toast.LENGTH_SHORT).show();
    }

    public void showAlert(int pos){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_client_details);

        ImageView closeIv = dialog.findViewById(R.id.close_iv);

        TextView clientName = dialog.findViewById(R.id.clientName_tv);
        TextView clientPhone = dialog.findViewById(R.id.telephone_tv);

        TextView clientCtNum = dialog.findViewById(R.id.clientNum_tv);
        TextView clientAdress = dialog.findViewById(R.id.clientAdress_tv);
        TextView clientStatus = dialog.findViewById(R.id.status_tv);
        TextView clientRegion = dialog.findViewById(R.id.client_region_tv);




        clientName.setText(clientsList.get(pos).getCT_Intitule());
        clientCtNum.setText(clientsList.get(pos).getCT_Num());
        clientAdress.setText(clientsList.get(pos).getCT_Adresse());
        clientStatus.setText("Client "+clientsList.get(pos).getStatutC());
        clientRegion.setText(clientsList.get(pos).getRegionName());
        clientPhone.setText("+216 "+clientsList.get(pos).getCT_Telephone());

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