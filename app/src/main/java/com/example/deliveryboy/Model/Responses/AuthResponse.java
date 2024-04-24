package com.example.deliveryboy.Model.Responses;

import com.example.deliveryboy.Model.Role;
import com.example.deliveryboy.Model.User;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("token")
    private String token;
    @SerializedName("msg")
    private String msg;

    @SerializedName("searcheduser")
    private User user;

    public AuthResponse(String token, String msg, User user) {
        this.token = token;
        this.msg = msg;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", msg='" + msg + '\'' +
                ", user=" + user +
                '}';
    }
}
