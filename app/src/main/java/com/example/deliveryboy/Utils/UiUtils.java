package com.example.deliveryboy.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import com.example.deliveryboy.R;
import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;

public class UiUtils {

    public static void showSnackbar(View view,String text,String actionText){
        Snackbar snackbar = Snackbar.make(view,text,Snackbar.LENGTH_LONG);
        snackbar.setAction(actionText, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        /*
        snackbar.setActionTextColor(Color.parseColor("#F8981D"));
        snackbar.setTextColor(Color.parseColor("#F8981D"));

        snackbar.setBackgroundTint(Color.parseColor("#FEF0DD"));

         */



        snackbar.setActionTextColor(Color.parseColor("#FFFFFF"));
        snackbar.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.setBackgroundTint(Color.parseColor("#F8981D"));


        snackbar.show();
    }


    public static void setupProgressBar(Context context, ProgressBar progressBar){

        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.orange_btn_color),
                PorterDuff.Mode.SRC_IN);
    }
    public static boolean isSessionExpired(Context context){

        boolean res = false;

        Calendar calendar = Calendar.getInstance();
        Long seconds=calendar.getTimeInMillis();
        if (seconds > SessionManager.getInstance().getExpireDate(context)){
            res=true;

            /*
            SessionManager.getInstance().setToken(context, "#");
            SessionManager.getInstance().setUserId(context, "-1");
            navController.navigate(R.id.action_menuCoFragment_to_loginFragment);

             */
        }
        return res;

    }

    @SuppressLint("ResourceAsColor")
    public static void setStatusBarColorDesign(Window window){
        window.setStatusBarColor(R.color.orange_btn_color);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
}
