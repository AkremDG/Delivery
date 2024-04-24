package com.example.deliveryboy.View.MissionsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.RegionsRvAdapter;
import com.example.deliveryboy.Adapters.TypeCmdRvAdapter;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.UiUtils;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class MissionDetails extends AppCompatActivity {
    AppBarLayout appBarLayout;
    private RecyclerView typeCmd_Rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_details);

        getWindow().setStatusBarColor(getResources().getColor(R.color.orange_btn_color));



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


    }

}