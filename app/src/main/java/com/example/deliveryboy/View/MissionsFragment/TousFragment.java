package com.example.deliveryboy.View.MissionsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Model.Commande;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.R;

import java.util.ArrayList;
import java.util.List;


public class TousFragment extends Fragment {
    RecyclerView listCmds_Rv;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =  inflater.inflate(R.layout.fragment_tous, container, false);
        bindViews();

        List<Visite> visiteList = new ArrayList<>();

        User user = new User(0,"Akrem BEN DHIA");
        Visite visite = new Visite("VISITE 01","visite de réactivation",user
        ,"Tunis");


        User user2 = new User(1,"Raslen");
        Visite visite2 = new Visite("VISITE 02","autre visite",user
                ,"Marsa");


        User user3 = new User(1,"Karim");
        Visite visite3 = new Visite("VISITE 02","autre visite",user
                ,"Marsa");


        User user4 = new User(2,"Raslen");
        Visite visite4 = new Visite("VISITE 01","autre visite",user
                ,"Ariana");




        visiteList.add(visite);
        visiteList.add(visite2);
        visiteList.add(visite3);
        visiteList.add(visite4);

        listCmds_Rv.setLayoutManager(new LinearLayoutManager(getContext()));


        listCmds_Rv.setAdapter(new MissionsRvAdapter(getContext(),visiteList));



        return view;
    }
    public void bindViews(){
        listCmds_Rv=view.findViewById(R.id.listCmds_Rv);
    }
}