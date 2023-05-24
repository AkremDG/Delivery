package com.example.deliveryboy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.deliveryboy.View.BottomNavFragments.MissionsFragment;
import com.example.deliveryboy.View.BottomNavFragments.ClientsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNagContainerActivity extends AppCompatActivity {
    MissionsFragment missionsFragment;
    ClientsFragment clientsFragment;

   BottomNavigationView bottom_nav_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nag_container);



        //White status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(BottomNagContainerActivity.this,R.color.white));

        bindViews();
        bindFragments();


        missionsFragment = new MissionsFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, missionsFragment).commit();

        bottom_nav_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Menu menu = bottom_nav_view.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    if (menuItem.getItemId() != item.getItemId()) {
                        switch (menuItem.getItemId()) {

                            case R.id.Missions:
                                menuItem.setIcon(getResources().getDrawable(R.drawable.empty_panier_icon));
                                break;

                            case R.id.clients:

                                menuItem.setIcon(getResources().getDrawable(R.drawable.empty_clients_icon));
                                break;
                        }
                    }
                }

                switch (item.getItemId()) {

                    case R.id.Missions:
                        missionsFragment = new MissionsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, missionsFragment).commit();
                        item.setIcon(getResources().getDrawable(R.drawable.filled_missions_icon));
                        return true;

                    case R.id.clients:
                        clientsFragment = new ClientsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, clientsFragment).commit();
                        item.setIcon(getResources().getDrawable(R.drawable.filled_clients_icon));
                        return true;

                }
                return false;
            }
        });


    }
    public void bindViews(){
        bottom_nav_view=findViewById(R.id.bottom_nav_view);
    }
    public void bindFragments(){




    }

}