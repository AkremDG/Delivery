package com.example.deliveryboy.View.MissionsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryboy.Adapters.ClientsRvAdapter;
import com.example.deliveryboy.Adapters.RegionClick;
import com.example.deliveryboy.Adapters.RegionsRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Model.Client;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.R;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.ViewModel.MissionsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

       // getWindow().setStatusBarColor(getResources().getColor(R.color.orange_btn_color));

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
        //appBarLayout.setOutlineProvider(null);

        typeCmd_Rv = findViewById(R.id.typeCmd_Rv);

        detailsMissions_mt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MissionDetails.this, BottomNagContainerActivity.class);
                startActivity(intent);
            }
        });

        HandleEvents();

        searchBar_tt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Client> filtredClientList = new ArrayList<>();

                for(Client client : clientsList){
                    if(client.getCT_Intitule().toLowerCase().contains(s) || client.getCT_Intitule().toUpperCase(Locale.ROOT).contains(s)){
                        filtredClientList.add(client);
                        setClientsList(filtredClientList);
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
        missionsClients_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        animateViewVisibilityWithFadeOut(searchBar_tt, null);
                        animateViewVisibilityWithFadeOut(constraint_date_end, null);
                        animateViewVisibilityWithFadeOut(constraint_date_start, null);
                        animateViewVisibilityWithFadeOut(startDateIv, null);
                        animateViewVisibilityWithFadeOut(endDatetextTv, null);
                        animateViewVisibilityWithFadeOut(clientAVisiterTv, () -> isAnimating = false);
                    }
                } else if (scrollY < oldScrollY - SCROLL_THRESHOLD && !isAnimating) {
                    // Scrolling up
                    if (scrolledDown) {
                        scrolledDown = false;
                        isAnimating = true;
                        // Show the views with animation
                        animateViewVisibilityWithFadeIn(searchBar_tt, null);
                        animateViewVisibilityWithFadeIn(constraint_date_end, null);
                        animateViewVisibilityWithFadeIn(constraint_date_start, null);
                        animateViewVisibilityWithFadeIn(startDateIv, null);
                        animateViewVisibilityWithFadeIn(endDatetextTv, null);
                        animateViewVisibilityWithFadeIn(clientAVisiterTv, () -> isAnimating = false);
                    }
                }

                oldScrollY = scrollY;
            }

            private void animateViewVisibilityWithFadeOut(final View view, final Runnable onAnimationEnd) {
                if (view != null && view.getVisibility() == View.VISIBLE) {
                    // Cancel any ongoing animations
                    view.animate().cancel();

                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f);
                    alphaAnimator.setDuration(300);
                    alphaAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(View.GONE);
                            if (onAnimationEnd != null) onAnimationEnd.run();
                        }
                    });
                    alphaAnimator.start();
                }
            }

            private void animateViewVisibilityWithFadeIn(final View view, final Runnable onAnimationEnd) {
                if (view != null && view.getVisibility() != View.VISIBLE) {
                    // Cancel any ongoing animations
                    view.animate().cancel();

                    view.setAlpha(0f);
                    view.setVisibility(View.VISIBLE);
                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);
                    alphaAnimator.setDuration(300);
                    alphaAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (onAnimationEnd != null) onAnimationEnd.run();
                        }
                    });
                    alphaAnimator.start();
                }
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