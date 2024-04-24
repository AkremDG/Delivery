package com.example.deliveryboy.Model;

import com.google.gson.annotations.SerializedName;

public class Client {
    @SerializedName("2317")
    private Integer clientId;
    @SerializedName("CT_Num")
    private String  CT_Num;
    @SerializedName("CT_Intitule")
    private String CT_Intitule;
    @SerializedName("CT_Adresse")
    private String CT_Adresse;
    @SerializedName("statutC")
    private String statutC;
    @SerializedName("CT_Telephone")
    private String CT_Telephone;
    @SerializedName("CT_Ville")
    private String CT_Ville;
    @SerializedName("Region")
    private Long regionId;
    @SerializedName("regionU")
    private Region region;


    public Client(Integer clientId, String CT_Num, String CT_Intitule, String CT_Adresse, String statutC, String CT_Telephone, String CT_Ville, Long regionId, Region region) {
        this.clientId = clientId;
        this.CT_Num = CT_Num;
        this.CT_Intitule = CT_Intitule;
        this.CT_Adresse = CT_Adresse;
        this.statutC = statutC;
        this.CT_Telephone = CT_Telephone;
        this.CT_Ville = CT_Ville;
        this.regionId = regionId;
        this.region = region;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCT_Num() {
        return CT_Num;
    }

    public void setCT_Num(String CT_Num) {
        this.CT_Num = CT_Num;
    }

    public String getCT_Intitule() {
        return CT_Intitule;
    }

    public void setCT_Intitule(String CT_Intitule) {
        this.CT_Intitule = CT_Intitule;
    }

    public String getCT_Adresse() {
        return CT_Adresse;
    }

    public void setCT_Adresse(String CT_Adresse) {
        this.CT_Adresse = CT_Adresse;
    }

    public String getStatutC() {
        return statutC;
    }

    public void setStatutC(String statutC) {
        this.statutC = statutC;
    }

    public String getCT_Telephone() {
        return CT_Telephone;
    }

    public void setCT_Telephone(String CT_Telephone) {
        this.CT_Telephone = CT_Telephone;
    }

    public String getCT_Ville() {
        return CT_Ville;
    }

    public void setCT_Ville(String CT_Ville) {
        this.CT_Ville = CT_Ville;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", CT_Num='" + CT_Num + '\'' +
                ", CT_Intitule='" + CT_Intitule + '\'' +
                ", CT_Adresse='" + CT_Adresse + '\'' +
                ", statutC='" + statutC + '\'' +
                ", CT_Telephone='" + CT_Telephone + '\'' +
                ", CT_Ville='" + CT_Ville + '\'' +
                ", regionId=" + regionId +
                ", region=" + region +
                '}';
    }
}
