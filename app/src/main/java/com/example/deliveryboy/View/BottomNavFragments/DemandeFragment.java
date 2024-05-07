package com.example.deliveryboy.View.BottomNavFragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryboy.Adapters.CategoriesAdapter.CatalogClick;
import com.example.deliveryboy.Adapters.CategoriesAdapter.ProductCatalogRv;
import com.example.deliveryboy.Adapters.DemandeVpAdapter;
import com.example.deliveryboy.Adapters.MissionsVpAdapter;
import com.example.deliveryboy.Adapters.ProduitRvAdapter;
import com.example.deliveryboy.Adapters.RvInterface;
import com.example.deliveryboy.Adapters.TypeProduitInterface;
import com.example.deliveryboy.Adapters.quantiteInterface;
import com.example.deliveryboy.Model.Produit;
import com.example.deliveryboy.Model.TypeCommande;
import com.example.deliveryboy.Model.User;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.SessionManager;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.SplashLoginActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DemandeFragment extends Fragment {
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private DemandeVpAdapter demandeVpAdapter;
    private View view;
    private ImageView logoutIv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view =  inflater.inflate(R.layout.demande_fragment, container, false);


        bindViews();
        uiSetup();
        uiListeners();


        return view;
    }

    private void uiSetup() {
        demandeVpAdapter = new DemandeVpAdapter(DemandeFragment.this);
        viewPager2.setAdapter(demandeVpAdapter);
    }

    private void uiListeners() {

        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SplashLoginActivity.class);
                SessionManager.getInstance().setToken(getContext(), "");
                getActivity().overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                startActivity(intent);
            }
        });

    }

    public void bindViews() {
        appBarLayout = view.findViewById(R.id.appBarLayout);
        appBarLayout.setOutlineProvider(null);
        logoutIv = view.findViewById(R.id.logout);
        tabLayout = view.findViewById(R.id.cmds_Tl);
        viewPager2 = view.findViewById(R.id.demandeVp);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                super.onPageSelected(position);
            }
        });


    }


}