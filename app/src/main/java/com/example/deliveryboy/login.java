package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class login extends AppCompatActivity {
   TextView mdp_oublie_Tv;
   MaterialButton connect_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //White status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(login.this,R.color.white));

        bindViews();
        clicksHandler();


    }
    public void bindViews()
    {
        mdp_oublie_Tv=findViewById(R.id.mdp_oublie_Tv);
        connect_btn=findViewById(R.id.connect_btn);
    }
    public void clicksHandler(){
        mdp_oublie_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,ResetPassword.class);
                startActivity(intent);
            }
        });

        connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,BottomNagContainer.class);
                startActivity(intent);
            }
        });

    }
}