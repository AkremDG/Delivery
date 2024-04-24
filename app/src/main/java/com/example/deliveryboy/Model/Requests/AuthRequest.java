package com.example.deliveryboy.Model.Requests;

import com.example.deliveryboy.Model.User;
import com.google.gson.annotations.SerializedName;

public class AuthRequest {

    @SerializedName("phone")
    private String phone;
    @SerializedName("key")
    private String password;

    @SerializedName("searcheduser")
    private User user;


    public AuthRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                '}';
    }
}
