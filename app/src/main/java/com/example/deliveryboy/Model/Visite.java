package com.example.deliveryboy.Model;

public class Visite {
    private String idVisite;
    private String typeVisite;
    private User user;
    private String zone;

    public Visite(String idVisite, String typeVisite, User user, String zone) {
        this.idVisite = idVisite;
        this.typeVisite = typeVisite;
        this.user = user;
        this.zone = zone;
    }

    public String getIdVisite() {
        return idVisite;
    }

    public void setIdVisite(String idVisite) {
        this.idVisite = idVisite;
    }

    public String getTypeVisite() {
        return typeVisite;
    }

    public void setTypeVisite(String typeVisite) {
        this.typeVisite = typeVisite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "Visite{" +
                "idVisite='" + idVisite + '\'' +
                ", typeVisite='" + typeVisite + '\'' +
                ", user=" + user +
                ", zone='" + zone + '\'' +
                '}';
    }
}
