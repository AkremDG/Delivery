package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class activity_Splash_Login extends AppCompatActivity {
    ConstraintLayout constraint;
   TextView mdp_oublie_Tv;
   MaterialButton connect_btn;
   ImageView login_Iv;
   Guideline guideline_top;
   TextInputLayout username_Til,password_Til;
   EditText username_Et,password_Et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
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
        username_Til=findViewById(R.id.username_Til);
        password_Til=findViewById(R.id.password_Til);
        username_Et=findViewById(R.id.username_Et);
        password_Et=findViewById(R.id.password_Et);
    }
    public void clicksHandler(){
        password_Til.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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
                if(!isEmptyInputs())
                {
                    Intent intent=new Intent(activity_Splash_Login.this,BottomNagContainer.class);
                    startActivity(intent);
                }

            }
        });
    }

    public Boolean isEmptyInputs(){
        boolean res=false;
        if(username_Til.getEditText().getText().toString().isEmpty()){
            username_Til.setError("Vérifier votre téléphone");
            username_Til.setStartIconTintList(getResources().getColorStateList(R.color.error_red));
            res=true;
        }
        if(password_Til.getEditText().getText().toString().isEmpty()){
            password_Til.setError("Vérifier votre mot de passe");
            password_Til.setStartIconTintList(getResources().getColorStateList(R.color.error_red));
            res=true;
        }
        return res;
    }

    }



