package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryboy.R;
import com.google.android.material.button.MaterialButton;

public class EmailSuccessActivity extends AppCompatActivity {
    private TextView redirect_Tv;
    private MaterialButton return_auth_btn;
    private ImageView arrow_Iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_success);


        bindViews();
        uiListeners();

    }

    public void bindViews() {

        redirect_Tv = findViewById(R.id.redirect_Tv);
        return_auth_btn = findViewById(R.id.return_auth_btn);
        arrow_Iv = findViewById(R.id.arrow_Iv);

    }

    public void uiListeners() {
        redirect_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailSuccessActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        arrow_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        return_auth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailSuccessActivity.this, SplashLoginActivity.class);
                startActivity(intent);
            }
        });


    }

}