package com.example.deliveryboy.Model;

import com.google.gson.annotations.SerializedName;

public class Role {
    @SerializedName("role")
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                '}';
    }
}
