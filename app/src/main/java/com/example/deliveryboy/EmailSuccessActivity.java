package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

public class EmailSuccessActivity extends AppCompatActivity {
 TextView redirect_Tv;
 MaterialButton return_auth_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_success);

        getWindow().setStatusBarColor(ContextCompat.getColor(EmailSuccessActivity.this,R.color.white));

        bindViews();
        clickHandler();

    }
    public void bindViews(){

        redirect_Tv=findViewById(R.id.redirect_Tv);
        return_auth_btn=findViewById(R.id.return_auth_btn);
    }

    public void clickHandler(){
        redirect_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailSuccessActivity.this,ResetPassword.class);
                startActivity(intent);
            }
        });

        return_auth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailSuccessActivity.this,login.class);
                startActivity(intent);
            }
        });


    }

}