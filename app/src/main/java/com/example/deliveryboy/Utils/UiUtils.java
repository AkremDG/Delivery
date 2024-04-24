package com.example.deliveryboy.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;

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
        snackbar.setActionTextColor(Color.WHITE);

        snackbar.setBackgroundTint(Color.BLACK);
        snackbar.show();
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
