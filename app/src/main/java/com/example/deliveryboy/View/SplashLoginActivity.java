package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryboy.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class SplashLoginActivity extends AppCompatActivity {
    boolean res=false;

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
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashLoginActivity.this,R.color.search_bg_color));


        bindViews();
        clicksHandler();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

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

                Intent intent=new Intent(SplashLoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmptyInputs())
                {
                    Intent intent=new Intent(SplashLoginActivity.this, BottomNagContainerActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    public Boolean isEmptyInputs(){

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

        username_Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!username_Et.getText().toString().isEmpty()){
                    username_Til.setError(null);
                    username_Til.setStartIconTintList(getResources().getColorStateList(R.color.start_icon_color));
                    res=false;

                }else
                {
                    username_Til.setError("Vérifier votre téléphone");
                    username_Til.setStartIconTintList(getResources().getColorStateList(R.color.error_red));
                    res=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        password_Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!password_Et.getText().toString().isEmpty()){
                    password_Til.setError(null);
                    password_Til.setStartIconTintList(getResources().getColorStateList(R.color.start_icon_color));
                    res=false;

                }else
                {
                    password_Til.setError("Vérifier votre téléphone");
                    password_Til.setStartIconTintList(getResources().getColorStateList(R.color.error_red));
                    res=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return res;
    }
    }



