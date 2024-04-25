package com.example.deliveryboy.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.deliveryboy.Model.Client;
import com.example.deliveryboy.Model.Mission;

import java.util.List;

@Dao
public interface MissionsDao {

    @Insert
    void insertAllMissions(List<Mission> missionList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMissionClient(Client missionClient);


    @Query("DELETE FROM Client")
    void deleteAllClientsMissions();


    @Query("DELETE FROM MISSION")
    void deleteAllMissions();


    @Query("SELECT * FROM Mission WHERE userId =:userId")
    List<Mission> getAllMissionsByUserId(Integer userId);

    @Query("SELECT * FROM Client WHERE missionId =:missionsId")
    List<Client> getAllClientsByMissionId(Integer missionsId);

}
