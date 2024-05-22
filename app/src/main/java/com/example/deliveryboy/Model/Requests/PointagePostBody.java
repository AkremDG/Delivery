package com.example.deliveryboy.Model.Requests;

import android.graphics.Point;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PointagePostBody {

    @SerializedName("id")
    private int idCmdBo;

    @SerializedName("id_car")
    private int idCar;

    @SerializedName("items")
    private List<PointageItems> pointageItemsList;


    public PointagePostBody(){

    }
    public PointagePostBody(int idCmdBo, int idCar, List<PointageItems> pointageItemsList) {
        this.idCmdBo = idCmdBo;
        this.idCar = idCar;
        this.pointageItemsList = pointageItemsList;
    }

    public int getIdCmdBo() {
        return idCmdBo;
    }

    public void setIdCmdBo(int idCmdBo) {
        this.idCmdBo = idCmdBo;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public List<PointageItems> getPointageItemsList() {
        return pointageItemsList;
    }

    public void setPointageItemsList(List<PointageItems> pointageItemsList) {
        this.pointageItemsList = pointageItemsList;
    }

    @Override
    public String toString() {
        return "PointagePostBody{" +
                "idCmdBo=" + idCmdBo +
                ", idCar=" + idCar +
                ", pointageItemsList=" + pointageItemsList +
                '}';
    }
}
