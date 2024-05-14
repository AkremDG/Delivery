package com.example.deliveryboy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Demande {

    @SerializedName("items")
    private List<DemandeProduitItem> demandeProduitItemList;

    @SerializedName("total")
    private Double total;

    public Demande(List<DemandeProduitItem> demandeProduitItemList, Double total) {
        this.demandeProduitItemList = demandeProduitItemList;
        this.total = total;
    }

    public List<DemandeProduitItem> getDemandeProduitItemList() {
        return demandeProduitItemList;
    }

    public void setDemandeProduitItemList(List<DemandeProduitItem> demandeProduitItemList) {
        this.demandeProduitItemList = demandeProduitItemList;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "demandeProduitItemList=" + demandeProduitItemList +
                ", total=" + total +
                '}';
    }
}
