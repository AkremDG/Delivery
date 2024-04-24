package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.deliveryboy.R;

import java.util.ArrayList;
import java.util.List;

public class RapportVisite extends AppCompatActivity {

    private ImageView arrow_pass_cmd_Iv;
    private List<String> raisonsList = new ArrayList<>();
    private ArrayAdapter adapter ;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_visite);

        bindViews();
        DisplayData();
        HandleEvents();

    }

    private void DisplayData() {
        raisonsList.add("Raison10");
        raisonsList.add("Raison12");
        raisonsList.add("Raison13");
        raisonsList.add("Raison13");
        raisonsList.add("Raison15");

        adapter = new ArrayAdapter(RapportVisite.this,   android.R.layout.simple_spinner_item,raisonsList);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void bindViews() {
        getWindow().setStatusBarColor(ContextCompat.getColor(RapportVisite.this,R.color.search_bg_color));
        arrow_pass_cmd_Iv=findViewById(R.id.arrow_pass_cmd_Iv);
        autoCompleteTextView = findViewById(R.id.raison_Actv);

    }
    private void HandleEvents(){
        arrow_pass_cmd_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RapportVisite.this, BottomNagContainerActivity.class);
                startActivity(intent);
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("SELECTED",parent.getItemAtPosition(position).toString());
            }
        });
    }
}