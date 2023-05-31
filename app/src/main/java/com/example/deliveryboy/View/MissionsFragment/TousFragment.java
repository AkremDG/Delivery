package com.example.deliveryboy.View.MissionsFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.View.PassCommandeActivity;
import com.example.deliveryboy.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TousFragment extends Fragment implements RvInterface {
    RecyclerView listCmds_Rv;
    View view;
    List<Visite> visiteList ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =  inflater.inflate(R.layout.fragment_tous, container, false);
        bindViews();


        visiteList = new ArrayList<>();

        User user = new User(0,"Akrem BEN DHIA");
        Visite visite = new Visite("VISITE 01","visite de réactivation",user
        ,"Tunis");


        User raslen = new User(0,"Raslen");
        Visite visiteralen = new Visite("VISITE 01","Autre visite",raslen
                ,"Tunis");




        visiteList.add(visite);
        visiteList.add(visiteralen);


        listCmds_Rv.setLayoutManager(new LinearLayoutManager(getContext()));


        listCmds_Rv.setAdapter(new MissionsRvAdapter(getContext(),visiteList,TousFragment.this));





        return view;
    }
    public void bindViews(){
        listCmds_Rv=view.findViewById(R.id.listCmds_Rv);
    }

    @Override
    public void onItemClick(int position) {

        showAlert(visiteList.get(position).getUser());

    }
    public void showAlert(User user){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        TextView generatrive_tv =dialog.findViewById(R.id.generatrice);
        TextView non_generatrive_tv = dialog.findViewById(R.id.non_generatrice);

        generatrive_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatrive_tv.setTextColor(getResources().getColor(R.color.blue_reset_password));
                Intent intent = new Intent(getContext(), PassCommandeActivity.class);
                intent.putExtra("user", (Serializable) user);

                startActivity(intent);
            }
        });

        non_generatrive_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatrive_tv.setTextColor(getResources().getColor(R.color.black));
                Toast.makeText(getContext(), "non_generatrive_tv", Toast.LENGTH_SHORT).show();
                non_generatrive_tv.setTextColor(getResources().getColor(R.color.blue_reset_password));

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


    }


}