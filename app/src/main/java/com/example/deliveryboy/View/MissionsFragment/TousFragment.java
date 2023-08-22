package com.example.deliveryboy.View.MissionsFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryboy.Adapters.MissionsRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.Model.Visite;
import com.example.deliveryboy.View.PanierActivity;
import com.example.deliveryboy.View.PassCommandeActivity;
import com.example.deliveryboy.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TousFragment extends Fragment implements RvInterface {
    private RecyclerView listCmds_Rv;
    private View view;
    private List<Visite> visiteList ;
    private SearchView searchBar_tt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =  inflater.inflate(R.layout.fragment_tous, container, false);

        bindViews();
        fakeMissionsData();
        DisplayData(visiteList);
        HandleEvents();

        return view;
    }

    private void HandleEvents() {
        listCmds_Rv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            private int scrollThreshold = 20;
            private boolean scrolledDown = false;
            private int oldScrollY = 0;

            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY + scrollThreshold) {
                    // Scrolling down
                    if (!scrolledDown) {
                        scrolledDown = true;
                        // Hide the views with animation
                        animateViewVisibilityWithFadeOut(searchBar_tt);
                    }
                } else if (scrollY < oldScrollY - scrollThreshold) {
                    // Scrolling up
                    if (scrolledDown) {
                        scrolledDown = false;
                        // Show the views with animation
                        animateViewVisibilityWithFadeIn(searchBar_tt);
                    }
                }

                oldScrollY = scrollY;
            }

            private void animateViewVisibilityWithFadeOut(final View view) {
                view.animate()
                        .alpha(0f)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                view.setVisibility(View.GONE);
                            }
                        })
                        .start();
            }

            private void animateViewVisibilityWithFadeIn(final View view) {
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0f);
                view.animate()
                        .alpha(1f)
                        .start();
            }

        });


        searchBar_tt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Visite> listVisists = new ArrayList<>();
                boolean dataFound = false;

                if (newText.length()==0) {
                    DisplayData(visiteList);
                }
                else{

                    for (Visite visite : visiteList) {
                        if (visite.getTypeVisite().contains(newText) || visite.getZone().contains(newText) || visite.getUser().getNameUser().contains(newText)) {
                            listVisists.add(visite);
                            dataFound = true;
                        }
                    }
                    if (dataFound) {
                        DisplayData(listVisists);
                    } else {
                        listCmds_Rv.setAdapter(null);
                    }

            }
                    return false;
                }

        });


    }

    private void DisplayData(List<Visite> visites) {

        listCmds_Rv.setLayoutManager(new LinearLayoutManager(getContext()));
        listCmds_Rv.setAdapter(new MissionsRvAdapter(getContext(), visites, TousFragment.this));


    }

    public void bindViews(){
        listCmds_Rv=view.findViewById(R.id.listCmds_Rv);
        searchBar_tt = view.findViewById(R.id.searchBar_tt);

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


    public void fakeMissionsData(){
        visiteList = new ArrayList<>();

        User user = new User(0,"Akrem BEN DHIA");
        Visite visite = new Visite("VISITE 01","visite de réactivation",user
                ,"Ariana");


        User raslen = new User(0,"Raslen");
        Visite visiteralen = new Visite("VISITE 01","Autre",raslen
                ,"Tunis");

        User user3 = new User(0,"Ali BEN SALEM");
        Visite visite0 = new Visite("VISITE 01","visite de reactivation",user3
                ,"Jandouba");


        User raslen4 = new User(0,"Kamel");
        Visite visiteralenz = new Visite("VISITE 01","Autre",raslen4
                ,"Beja");


        visiteList.add(visite);
        visiteList.add(visiteralen);
        visiteList.add(visite0);
        visiteList.add(visiteralenz);

        visiteList.add(visite);
        visiteList.add(visiteralen);
        visiteList.add(visite0);
        visiteList.add(visiteralenz);

        visiteList.add(visite);
        visiteList.add(visiteralen);
        visiteList.add(visite0);
        visiteList.add(visiteralenz);

        visiteList.add(visite);
        visiteList.add(visiteralen);
        visiteList.add(visite0);
        visiteList.add(visiteralenz);
    }
}