package com.example.deliveryboy.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.deliveryboy.View.BottomNavFragments.DemandeFragment;
import com.example.deliveryboy.View.DemandeFragments.TousDemandesFragment;
import com.example.deliveryboy.View.DemandeFragments.ValidDemandsFragment;

public class DemandeVpAdapter extends FragmentStateAdapter {


    public DemandeVpAdapter(@NonNull DemandeFragment demandeFragment) {
        super(demandeFragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 : return new TousDemandesFragment();
            case 1 : return new ValidDemandsFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
