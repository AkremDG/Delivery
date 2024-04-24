package com.example.deliveryboy.View.BottomNavFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.deliveryboy.Adapters.MissionsVpAdapter;
import com.example.deliveryboy.Model.Mission;
import com.example.deliveryboy.R;
import com.example.deliveryboy.Utils.SessionManager;
import com.example.deliveryboy.Utils.UiUtils;
import com.example.deliveryboy.View.BottomNagContainerActivity;
import com.example.deliveryboy.View.MainActivity;
import com.example.deliveryboy.View.SplashLoginActivity;
import com.example.deliveryboy.ViewModel.MissionsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class MissionsFragment extends Fragment {
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MissionsVpAdapter missionsVpAdapter;
    private View view;
    private ImageView logoutIv;

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_commandes, container, false);


        bindViews();
        uiSetup();
        uiListeners();


        return view;
    }

    private void uiSetup() {
        missionsVpAdapter = new MissionsVpAdapter(MissionsFragment.this);
        viewPager2.setAdapter(missionsVpAdapter);
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
        viewPager2 = view.findViewById(R.id.cmdsVp);

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