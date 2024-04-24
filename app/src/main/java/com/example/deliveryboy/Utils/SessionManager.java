package com.example.deliveryboy.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class SessionManager {

    private static final SessionManager instance = new SessionManager();
    public static final String DIGCOM_PREFS_NAME = "DigidPref";
    private final String TOKEN = "TOKEN";
    private final String USER_ID = "USER_ID";

    private final String ROLE_ID = "ROLE_ID";

    private final String EXPIRE_DATE = "EXPIRE_DATE";



    public static SessionManager getInstance() {
        return instance;
    }

    public void setToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(TOKEN, "");
    }


    public void setUSER_ID(Context context, Integer userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    public Integer getUSER_ID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(USER_ID, -1);
    }


    public void setROLE_ID(Context context, Integer roleId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(ROLE_ID, roleId);
        editor.apply();
    }

    public Integer getROLE_ID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(ROLE_ID, -1);
    }


    public void setExpireDate(Context context, Calendar calendar) {
        SharedPreferences.Editor editor = context.getSharedPreferences(DIGCOM_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(EXPIRE_DATE, calendar.getTimeInMillis());
        editor.apply();
    }

    public long getExpireDate(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(DIGCOM_PREFS_NAME, MODE_PRIVATE);
        return prefs.getLong(EXPIRE_DATE, -1);
    }



}
