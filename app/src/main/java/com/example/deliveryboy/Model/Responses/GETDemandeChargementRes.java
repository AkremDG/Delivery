package com.example.deliveryboy.Model.Responses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class GETDemandeChargementRes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localId")
    private int localId;

    @ColumnInfo(name = "boId")
    @SerializedName("id")
    private int boId;


    @SerializedName("id_car")
    @ColumnInfo(name = "id_car")
    private int id_car;


    @ColumnInfo(name = "NumCmd")
    @SerializedName("NumCmd")
    private String NumCmd;

    @ColumnInfo(name = "DO_Date")
    @SerializedName("DO_Date")
    private String DO_Date;

    @ColumnInfo(name = "Do_Time")
    @SerializedName("Do_Time")
    private String Do_Time;



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

    public GETDemandeChargementRes(int localId, int boId, int id_car, String numCmd, String DO_Date, String do_Time, Double DO_TotalHT, String statut, List<CmdLigne> cmdLigneList) {
        this.localId = localId;
        this.boId = boId;
        this.id_car = id_car;
        NumCmd = numCmd;
        this.DO_Date = DO_Date;
        Do_Time = do_Time;
        this.DO_TotalHT = DO_TotalHT;
        Statut = statut;
        this.cmdLigneList = cmdLigneList;
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

    public int getId_car() {
        return id_car;
    }

    public void setId_car(int id_car) {
        this.id_car = id_car;
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

    public String getDo_Time() {
        return Do_Time;
    }

    public void setDo_Time(String do_Time) {
        Do_Time = do_Time;
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
                ", id_car=" + id_car +
                ", NumCmd='" + NumCmd + '\'' +
                ", DO_Date='" + DO_Date + '\'' +
                ", Do_Time='" + Do_Time + '\'' +
                ", DO_TotalHT=" + DO_TotalHT +
                ", Statut='" + Statut + '\'' +
                ", cmdLigneList=" + cmdLigneList +
                '}';
    }
}
