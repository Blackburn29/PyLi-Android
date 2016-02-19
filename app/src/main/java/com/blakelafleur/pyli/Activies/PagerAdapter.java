package com.blakelafleur.pyli.Activies;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blakelafleur.pyli.Fragments.BasicColorSettingFragment;
import com.blakelafleur.pyli.Fragments.BlinkColorSettingFragment;
import com.blakelafleur.pyli.Fragments.FadeColorSettingFragment;

/**
 * Created by blake on 2/17/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_TABS = 3;
    private Class[] fragments = {
            BasicColorSettingFragment.class,
            FadeColorSettingFragment.class,
            BlinkColorSettingFragment.class
    };

    public PagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments.length - 1 < position) {
            return null;
        }
        try {
            return (Fragment) fragments[position].newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getCount() {
        return PagerAdapter.NUM_TABS;
    }
}
