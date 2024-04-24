package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.deliveryboy.R;
import com.google.android.material.button.MaterialButton;

public class ResetPasswordActivity extends AppCompatActivity {

    private MaterialButton envoyer_email_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        bindViews();
        uiListeners();
    }

    public void bindViews(){
        envoyer_email_btn=findViewById(R.id.envoyer_email_btn);
    }

    public void uiListeners(){

        envoyer_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResetPasswordActivity.this,EmailSuccessActivity.class);
                startActivity(intent);
            }
        });
    }
}