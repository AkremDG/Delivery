package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class activity_Splash_Login extends AppCompatActivity {
    ConstraintLayout constraint;
   TextView mdp_oublie_Tv;
   MaterialButton connect_btn;
   ImageView login_Iv;
   Guideline guideline_top;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);

        //White status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(activity_Splash_Login.this,R.color.white));

        bindViews();
        clicksHandler();


    }
    public void bindViews()
    {
        mdp_oublie_Tv=findViewById(R.id.mdp_oublie_Tv);
        connect_btn=findViewById(R.id.connect_btn);
        login_Iv=findViewById(R.id.login_Iv);
        guideline_top=findViewById(R.id.guideline_top);
        constraint=findViewById(R.id.constraint);
    }
    public void clicksHandler(){
        mdp_oublie_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_Splash_Login.this,ResetPassword.class);
                startActivity(intent);
            }
        });

        connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_Splash_Login.this,BottomNagContainer.class);
                startActivity(intent);
            }
        });

    }

















    }



