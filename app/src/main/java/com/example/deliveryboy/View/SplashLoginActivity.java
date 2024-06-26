package com.example.deliveryboy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Model.Requests.AuthRequest;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.InternetChecker;
import com.example.deliveryboy.Utils.SessionManager;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.ViewModel.AuthViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class SplashLoginActivity extends AppCompatActivity {
    private boolean res = false;
    private AuthViewModel authViewModel;
    private ConstraintLayout constraint;
    private TextView mdp_oublie_Tv;
    private MaterialButton connect_btn;
    private ImageView login_Iv;
    private Guideline guideline_top;
    private TextInputLayout username_Til, password_Til;
    private EditText username_Et, password_Et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);

        authViewModel = new AuthViewModel();

        bindViews();
        uiSetup();
        uiListeners();


    }

    private void uiSetup() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public void bindViews() {
        mdp_oublie_Tv = findViewById(R.id.mdp_oublie_Tv);
        connect_btn = findViewById(R.id.connect_btn);
        login_Iv = findViewById(R.id.login_Iv);
        guideline_top = findViewById(R.id.guideline_top);
        constraint = findViewById(R.id.constraint);
        username_Til = findViewById(R.id.username_Til);
        password_Til = findViewById(R.id.password_Til);
        username_Et = findViewById(R.id.username_Et);
        password_Et = findViewById(R.id.password_Et);
    }

    public void uiListeners() {
        password_Til.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mdp_oublie_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SplashLoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!isEmptyInputs()) {

                    if (InternetChecker.isConnectedToInternet(getApplicationContext())) {

                        connect_btn.setText("En cours ...");
                        connect_btn.setEnabled(false);

                        authViewModel.authUser(getApplicationContext(), new AuthRequest(username_Et.getText().toString(),
                                password_Et.getText().toString())).observe(SplashLoginActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {

                                connect_btn.setEnabled(true);
                                connect_btn.setText("SE CONNECTER");

                                if (s.equals("success")) {
                                    Intent intent = new Intent(SplashLoginActivity.this, BottomNagContainerActivity.class);
                                    startActivity(intent);
                                } else {

                                    UiUtils.showSnackbar(constraint, s, "Annuler");

                                }
                            }
                        });

                    } else {

                        UiUtils.showSnackbar(constraint, "Pas de connexion internet", "Annuler");
                    }

                }



            }
        });

    }

    public Boolean isEmptyInputs() {

        if (username_Til.getEditText().getText().toString().isEmpty()) {
            username_Til.setError("Vérifier votre téléphone");
            username_Til.setStartIconTintList(getResources().getColorStateList(R.color.error_red));
            res = true;
        }
        if (password_Til.getEditText().getText().toString().isEmpty()) {
            password_Til.setError("Vérifier votre mot de passe");
            password_Til.setStartIconTintList(getResources().getColorStateList(R.color.error_red));
            res = true;
        }

        username_Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!username_Et.getText().toString().isEmpty()) {
                    username_Til.setError(null);
                    username_Til.setStartIconTintList(getResources().getColorStateList(R.color.start_icon_color));
                    res = false;

                } else {
                    username_Til.setError("Vérifier votre téléphone");
                    username_Til.setStartIconTintList(getResources().getColorStateList(R.color.error_red));
                    res = true;
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
                if (!password_Et.getText().toString().isEmpty()) {
                    password_Til.setError(null);
                    password_Til.setStartIconTintList(getResources().getColorStateList(R.color.start_icon_color));
                    res = false;

                } else {
                    password_Til.setError("Vérifier votre téléphone");
                    password_Til.setStartIconTintList(getResources().getColorStateList(R.color.error_red));
                    res = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return res;
    }

}



