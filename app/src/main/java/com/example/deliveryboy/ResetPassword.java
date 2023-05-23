package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class ResetPassword extends AppCompatActivity {

    MaterialButton envoyer_email_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //White status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(ResetPassword.this,R.color.white));
        bindViews();
        clicksHandler();
    }

    public void bindViews(){
        envoyer_email_btn=findViewById(R.id.envoyer_email_btn);
    }

    public void clicksHandler(){

        envoyer_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResetPassword.this,EmailSuccessActivity.class);
                startActivity(intent);
            }
        });
    }
}