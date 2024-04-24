package com.example.deliveryboy.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Mission {
    @PrimaryKey
    @ColumnInfo(name = "missionId")
    @SerializedName("id")
    private Integer missionId;


    @ColumnInfo(name = "userId")
    @SerializedName("vendeur")
    private Integer userId;

    @ColumnInfo(name = "startOn")
    @SerializedName("startOn")
    private String startOn;

    @ColumnInfo(name = "endsOn")
    @SerializedName("endsOn")
    private String endsOn;


    @Ignore
    @SerializedName("clientU")
    private List<Client> clientsList;

    public Mission(){

    }

    public Mission(Integer missionId, Integer userId, String startOn, String endsOn,
                   List<Region> regionsList, List<Client> clientsList) {
        this.missionId = missionId;
        this.userId = userId;
        this.startOn = startOn;
        this.endsOn = endsOn;
        this.clientsList = clientsList;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStartOn() {
        return startOn;
    }

    public void setStartOn(String startOn) {
        this.startOn = startOn;
    }

    public String getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(String endsOn) {
        this.endsOn = endsOn;
    }


    public List<Client> getClientsList() {
        return clientsList;
    }

    public void setClientsList(List<Client> clientsList) {
        this.clientsList = clientsList;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "missionId=" + missionId +
                ", userId=" + userId +
                ", startOn='" + startOn + '\'' +
                ", endsOn='" + endsOn + '\'' +
                ", clientsList=" + clientsList +
                '}';
    }
}
