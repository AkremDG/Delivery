package com.example.deliveryboy.View.MissionsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.ClientsRvAdapter;
import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.RegionsRvAdapter;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.Model.Client;
import com.example.deliveryboy.Model.Region;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.UiUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MissionDetails extends AppCompatActivity {
    AppBarLayout appBarLayout;
    private RecyclerView typeCmd_Rv,missionsClients_rv;
    EditText searchBar_tt;
    ImageView calIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_details);

        getWindow().setStatusBarColor(getResources().getColor(R.color.orange_btn_color));

        missionsClients_rv = findViewById(R.id.missionsClients_rv);
        calIv = findViewById(R.id.calIv);
        searchBar_tt = findViewById(R.id.searchBar_tt);

        appBarLayout= findViewById(R.id.detailsAppbar);

        appBarLayout.setOutlineProvider(null);

        typeCmd_Rv = findViewById(R.id.typeCmd_Rv);

        List<String> regionsList = new ArrayList<>();
        regionsList.add("Tunis");
        regionsList.add("Bizerte");
        regionsList.add("Tunis");
        regionsList.add("Bizerte");
        regionsList.add("Tunis");
        regionsList.add("Bizerte");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        typeCmd_Rv.setLayoutManager(layoutManager);

        typeCmd_Rv.setAdapter(new RegionsRvAdapter(getApplicationContext(),regionsList));

        List<Client> clientList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            // You can generate different names dynamically, for example:
            String name = "Client " + (i + 1); // Generating names like "Client 1", "Client 2", ...

            // You can use other attributes with random values if needed
            String phoneNumber = "90987890";
            String address = "Mourouj";
            long clientId = i + 1; // Assuming client IDs start from 1

            // Create a new Client object and add it to the list
            clientList.add(new Client(0, "34", name, null, "Client indisponible",
                    phoneNumber, address, 99999999999L, new Region(4, "Tunis", 4)));
        }


        Toast.makeText(this, String.valueOf(clientList.get(0).getCT_Intitule()), Toast.LENGTH_SHORT).show();

        LinearLayoutManager layoutManagerClients = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        missionsClients_rv.setLayoutManager(layoutManagerClients);

        missionsClients_rv.setAdapter(new ClientsRvAdapter(getApplicationContext(),clientList));

        HandleEvents();

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

}