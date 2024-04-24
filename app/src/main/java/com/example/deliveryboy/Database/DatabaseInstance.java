package com.example.deliveryboy.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Region;
import com.example.deliveryboy.Model.User;

@Database(entities = {Mission.class}, version = 4,exportSchema = false)
public abstract class DatabaseInstance extends RoomDatabase {

    public static DatabaseInstance instance;

    public abstract MissionsDao missionsDao();

    public static synchronized DatabaseInstance getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context,DatabaseInstance.class,"DIGID_PAP.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}