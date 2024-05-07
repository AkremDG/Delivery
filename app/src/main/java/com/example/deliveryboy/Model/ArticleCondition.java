package com.example.deliveryboy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

@Entity
public class ArticleCondition {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="localIdCondition")
    private Integer localIdCondition;

    @ColumnInfo(name ="AS_QteSto")
    @SerializedName("AS_QteSto")
    private Double AS_QteSto;

    @ColumnInfo(name ="FA_CodeFamille")
    @SerializedName("FA_CodeFamille")
    private String FA_CodeFamille;


    @ColumnInfo(name ="DE_Intitule")
    @SerializedName("DE_Intitule")
    private String DE_Intitule;


    @ColumnInfo(name ="EC_Enumere")
    @SerializedName("EC_Enumere")
    private String EC_Enumere;

    @ColumnInfo(name ="TC_Prix")
    @SerializedName("TC_Prix")
    private Double TC_Prix;


    public ArticleCondition(){
    }


    public ArticleCondition(Integer localIdCondition, Double AS_QteSto, String FA_CodeFamille, String DE_Intitule, String EC_Enumere, Double TC_Prix) {
        this.localIdCondition = localIdCondition;
        this.AS_QteSto = AS_QteSto;
        this.FA_CodeFamille = FA_CodeFamille;
        this.DE_Intitule = DE_Intitule;
        this.EC_Enumere = EC_Enumere;
        this.TC_Prix = TC_Prix;
    }

    public Integer getLocalIdCondition() {
        return localIdCondition;
    }

    public void setLocalIdCondition(Integer localIdCondition) {
        this.localIdCondition = localIdCondition;
    }

    public Double getAS_QteSto() {
        return AS_QteSto;
    }

    public void setAS_QteSto(Double AS_QteSto) {
        this.AS_QteSto = AS_QteSto;
    }

    public String getFA_CodeFamille() {
        return FA_CodeFamille;
    }

    public void setFA_CodeFamille(String FA_CodeFamille) {
        this.FA_CodeFamille = FA_CodeFamille;
    }

    public String getDE_Intitule() {
        return DE_Intitule;
    }

    public void setDE_Intitule(String DE_Intitule) {
        this.DE_Intitule = DE_Intitule;
    }

    public String getEC_Enumere() {
        return EC_Enumere;
    }

    public void setEC_Enumere(String EC_Enumere) {
        this.EC_Enumere = EC_Enumere;
    }

    public Double getTC_Prix() {
        return TC_Prix;
    }

    public void setTC_Prix(Double TC_Prix) {
        this.TC_Prix = TC_Prix;
    }

    @Override
    public String toString() {
        return "ArticleCondition{" +
                "localIdCondition=" + localIdCondition +
                ", AS_QteSto=" + AS_QteSto +
                ", FA_CodeFamille='" + FA_CodeFamille + '\'' +
                ", DE_Intitule='" + DE_Intitule + '\'' +
                ", EC_Enumere='" + EC_Enumere + '\'' +
                ", TC_Prix=" + TC_Prix +
                '}';
    }
}
