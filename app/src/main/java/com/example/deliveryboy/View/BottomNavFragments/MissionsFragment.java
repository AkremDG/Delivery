package com.example.deliveryboy.View.BottomNavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deliveryboy.Adapters.MissionsVpAdapter;
import com.example.deliveryboy.R;
import com.google.android.material.tabs.TabLayout;


public class MissionsFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MissionsVpAdapter missionsVpAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_commandes, container, false);

        bindViews();

        missionsVpAdapter = new MissionsVpAdapter(MissionsFragment.this);
        viewPager2.setAdapter(missionsVpAdapter);


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

        return view;
    }
    public void bindViews(){
        tabLayout=view.findViewById(R.id.cmds_Tl);
        viewPager2=view.findViewById(R.id.cmdsVp);

    }
}