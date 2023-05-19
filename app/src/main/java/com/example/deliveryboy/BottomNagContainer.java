package com.example.deliveryboy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.deliveryboy.Menu.BuyFragment;
import com.example.deliveryboy.Menu.DocumentFragment;
import com.example.deliveryboy.Menu.HomeFragment;
import com.example.deliveryboy.Menu.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class BottomNagContainer extends AppCompatActivity {
    HomeFragment homeFragment;
    DocumentFragment documentFragment;
    BuyFragment buyFragment;
    ProfileFragment profileFragment;

   BottomNavigationView bottom_nav_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nag_container);



        //White status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(BottomNagContainer.this,R.color.white));

        bindViews();
        bindFragments();



        bottom_nav_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Menu menu = bottom_nav_view.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    if (menuItem.getItemId() != item.getItemId()) {
                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                menuItem.setIcon(getResources().getDrawable(R.drawable.empty_home_icon));
                                break;
                            case R.id.document:
                                menuItem.setIcon(getResources().getDrawable(R.drawable.empty_mission_icon));
                                break;
                            case R.id.buy:
                                menuItem.setIcon(getResources().getDrawable(R.drawable.empty_panier_icon));
                                break;
                            case R.id.profile:
                                menuItem.setIcon(getResources().getDrawable(R.drawable.empty_profile_icon));
                                break;
                        }
                    }
                }

                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        item.setIcon(getResources().getDrawable(R.drawable.filled_home_icon));
                        return true;
                    case R.id.document:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, documentFragment).commit();
                        item.setIcon(getResources().getDrawable(R.drawable.filled_document_icon));
                        return true;
                    case R.id.buy:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, buyFragment).commit();
                        item.setIcon(getResources().getDrawable(R.drawable.filled_buy_icon));
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        item.setIcon(getResources().getDrawable(R.drawable.filled_profile_icon));
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

         homeFragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

         documentFragment = new DocumentFragment();
         buyFragment = new BuyFragment();
         profileFragment = new ProfileFragment();
    }

}