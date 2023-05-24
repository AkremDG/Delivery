package com.example.deliveryboy.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.deliveryboy.Menu.CommandesFragment;
import com.example.deliveryboy.MenuCommandes.AtraiterFragment;
import com.example.deliveryboy.MenuCommandes.LivresFragment;
import com.example.deliveryboy.MenuCommandes.NonLivresFragment;
import com.example.deliveryboy.MenuCommandes.TousFragment;

public class CmdsVpAdapter extends FragmentStateAdapter {


    public CmdsVpAdapter(@NonNull CommandesFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TousFragment();
            case 1:
                return new LivresFragment();
            case 2:
                return new AtraiterFragment();
            case 3:
                return new NonLivresFragment();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
