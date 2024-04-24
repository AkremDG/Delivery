package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.SessionManager;
import com.example.deliveryboy.Utils.UiUtils;

import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spone);


        if (SessionManager.getInstance().getToken(MainActivity.this).isEmpty() || UiUtils.isSessionExpired(getApplicationContext())) {

            Intent intent = new Intent(MainActivity.this, SplashLoginActivity.class);
            startActivity(intent);

        } else {

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, BottomNagContainerActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }, 100);


        }


    }
}