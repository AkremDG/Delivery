package com.example.deliveryboy.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.deliveryboy.Model.ProduitCondition;
import com.example.deliveryboy.Model.Client;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.Responses.CmdLigne;
import com.example.deliveryboy.Model.Responses.GETDemandeChargementRes;

@Database(entities = {Mission.class, Client.class, Produit.class, ProduitCondition.class, GETDemandeChargementRes.class, CmdLigne.class}, version = 20,exportSchema = false)
public abstract class DatabaseInstance extends RoomDatabase {

    public static DatabaseInstance instance;

    public abstract MissionsDao missionsDao();

    public abstract DemadesChargDao demadesChargDao();


    public static synchronized DatabaseInstance getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context,DatabaseInstance.class,"DIGID_PAP.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}