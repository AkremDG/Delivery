package com.example.deliveryboy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Demande {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localDemandId")
    private int localDemandId;

    @ColumnInfo(name = "boId")
    @SerializedName("id")
    private int boId;

    @ColumnInfo(name = "ctNum")
    @SerializedName("ctNum")
    private int ctNum;


    @ColumnInfo(name = "numCmd")
    @SerializedName("NumCmd")
    private int numCmd;


    @ColumnInfo(name = "doPiece")
    @SerializedName("Do_Piece")
    private int doPiece;


    @ColumnInfo(name = "doDate")
    @SerializedName("DO_Date")
    private int doDate;

    @ColumnInfo(name = "doTotalHt")
    @SerializedName("doTotalHt")
    private int doTotalHt;

    @ColumnInfo(name = "isSync")
    @SerializedName("isSync")
    private Boolean isSync;

    @ColumnInfo(name = "statut")
    @SerializedName("Statut")
    private String statut;


    @Ignore
    @SerializedName("cmdLignes")
    private List<CmdLigne> cmdLignes;
}
