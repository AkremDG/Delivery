package com.example.deliveryboy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Client implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "localClientId")
    private Integer localClientId;

    @ColumnInfo(name = "boClientId")
    @SerializedName("id")
    private Integer clientId;

    @ColumnInfo(name = "missionId")
    private Integer missionId;

    @ColumnInfo(name = "CT_Num")
    @SerializedName("CT_Num")
    private String  CT_Num;

    @ColumnInfo(name = "CT_Intitule")
    @SerializedName("CT_Intitule")
    private String CT_Intitule;

    @ColumnInfo(name = "CT_Adresse")
    @SerializedName("CT_Adresse")
    private String CT_Adresse;


    @ColumnInfo(name = "statutC")
    @SerializedName("statutC")
    private String statutC;

    @ColumnInfo(name = "CT_Telephone")
    @SerializedName("CT_Telephone")
    private String CT_Telephone;

    @ColumnInfo(name = "CT_Ville")
    @SerializedName("CT_Ville")
    private String CT_Ville;


    @ColumnInfo(name = "regionId")
    @SerializedName("Region")
    private Long regionId;

    @ColumnInfo(name = "RegionName")
    @SerializedName("RegionName")
    private String RegionName;



    @Ignore
    @SerializedName("regionU")
    private Region region;

    public Client(){

    }

    public Client(Integer localClientId, Integer clientId, Integer missionId, String CT_Num, String CT_Intitule, String CT_Adresse, String statutC, String CT_Telephone, String CT_Ville, Long regionId, String regionName, Region region) {
        this.localClientId = localClientId;
        this.clientId = clientId;
        this.missionId = missionId;
        this.CT_Num = CT_Num;
        this.CT_Intitule = CT_Intitule;
        this.CT_Adresse = CT_Adresse;
        this.statutC = statutC;
        this.CT_Telephone = CT_Telephone;
        this.CT_Ville = CT_Ville;
        this.regionId = regionId;
        RegionName = regionName;
        this.region = region;
    }

    public Integer getLocalClientId() {
        return localClientId;
    }

    public void setLocalClientId(Integer localClientId) {
        this.localClientId = localClientId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
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

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
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
                "localClientId=" + localClientId +
                ", clientId=" + clientId +
                ", missionId=" + missionId +
                ", CT_Num='" + CT_Num + '\'' +
                ", CT_Intitule='" + CT_Intitule + '\'' +
                ", CT_Adresse='" + CT_Adresse + '\'' +
                ", statutC='" + statutC + '\'' +
                ", CT_Telephone='" + CT_Telephone + '\'' +
                ", CT_Ville='" + CT_Ville + '\'' +
                ", regionId=" + regionId +
                ", RegionName='" + RegionName + '\'' +
                ", region=" + region +
                '}';
    }
}
