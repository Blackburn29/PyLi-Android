package com.blakelafleur.pyli;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blakelafleur.pyli.Fragments.BasicColorSettingFragment;
import com.blakelafleur.pyli.Fragments.EffectsColorSettingFragment;

/**
 * Created by blake on 2/17/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_TABS = 2;

    public PagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return BasicColorSettingFragment.newInstance();
            case 1:
                return EffectsColorSettingFragment.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return PagerAdapter.NUM_TABS;
    }
}
