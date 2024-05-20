package com.example.deliveryboy.Model.Responses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class GETDemandeChargementRes {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localId")
    private int localId;

    @ColumnInfo(name = "boId")
    @SerializedName("id")
    private int boId;

    @ColumnInfo(name = "NumCmd")
    @SerializedName("NumCmd")
    private String NumCmd;

    @ColumnInfo(name = "DO_Date")
    @SerializedName("DO_Date")
    private String DO_Date;



    @ColumnInfo(name = "DO_TotalHT")
    @SerializedName("DO_TotalHT")
    private Double DO_TotalHT;



    @ColumnInfo(name = "Statut")
    @SerializedName("Statut")
    private String Statut;


    @Ignore
    @SerializedName("cmdLignes")
    private List<CmdLigne> cmdLigneList;

    public GETDemandeChargementRes(){

    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getBoId() {
        return boId;
    }

    public void setBoId(int boId) {
        this.boId = boId;
    }

    public String getNumCmd() {
        return NumCmd;
    }

    public void setNumCmd(String numCmd) {
        NumCmd = numCmd;
    }

    public String getDO_Date() {
        return DO_Date;
    }

    public void setDO_Date(String DO_Date) {
        this.DO_Date = DO_Date;
    }

    public Double getDO_TotalHT() {
        return DO_TotalHT;
    }

    public void setDO_TotalHT(Double DO_TotalHT) {
        this.DO_TotalHT = DO_TotalHT;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    public List<CmdLigne> getCmdLigneList() {
        return cmdLigneList;
    }

    public void setCmdLigneList(List<CmdLigne> cmdLigneList) {
        this.cmdLigneList = cmdLigneList;
    }

    @Override
    public String toString() {
        return "GETDemandeChargementRes{" +
                "localId=" + localId +
                ", boId=" + boId +
                ", NumCmd='" + NumCmd + '\'' +
                ", DO_Date='" + DO_Date + '\'' +
                ", DO_TotalHT=" + DO_TotalHT +
                ", Statut='" + Statut + '\'' +
                ", cmdLigneList=" + cmdLigneList +
                '}';
    }
}
