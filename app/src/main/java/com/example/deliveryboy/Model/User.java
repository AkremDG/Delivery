package com.example.deliveryboy.Model;

import java.io.Serializable;

public class User implements Serializable {
    private int idUser;
    private String nameUser;

    public User(int idUser, String nameUser) {
        this.idUser = idUser;
        this.nameUser = nameUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", nameUser='" + nameUser + '\'' +
                '}';
    }
}
