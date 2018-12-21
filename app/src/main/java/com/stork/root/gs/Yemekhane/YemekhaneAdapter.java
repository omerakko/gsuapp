package com.stork.root.gs.Yemekhane;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Calendar;

/**
 * Created by root on 16.03.2017.
 */
public class YemekhaneAdapter extends FragmentPagerAdapter {


    public YemekhaneAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {

        if(position==0)
            return new sabah_fragment();
        else if(position == 1)
            return new ogle_fragment();
        else
            return new aksam_fragment();

    }

    @Override
    public int getCount() {
        return 3;
    }
}
