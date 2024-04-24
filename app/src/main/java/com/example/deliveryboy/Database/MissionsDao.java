package com.example.deliveryboy.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.deliveryboy.Model.Mission;

import java.util.List;

@Dao
public interface MissionsDao {

    @Insert
    void insertAllMissions(List<Mission> missionList);

    @Query("DELETE FROM MISSION")
    void deleteAllMissions();


    @Query("SELECT * FROM Mission WHERE userId =:userId")
    List<Mission> getAllMissionsByUserId(Integer userId);

}
