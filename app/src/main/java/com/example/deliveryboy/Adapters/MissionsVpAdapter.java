package com.example.deliveryboy.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.deliveryboy.View.BottomNavFragments.MissionsFragment;
import com.example.deliveryboy.View.MissionsFragment.DétaillantFragment;
import com.example.deliveryboy.View.MissionsFragment.GrossisteFragment;
import com.example.deliveryboy.View.MissionsFragment.TousFragment;

public class MissionsVpAdapter extends FragmentStateAdapter {


    public MissionsVpAdapter(@NonNull MissionsFragment fragmentActivity) {
        super(fragmentActivity);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TousFragment();
            case 1:
                return new GrossisteFragment();
            case 2:
                return new DétaillantFragment();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
