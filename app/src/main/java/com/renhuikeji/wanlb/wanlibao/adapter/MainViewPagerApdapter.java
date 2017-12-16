package com.renhuikeji.wanlb.wanlibao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lxx on 2016/3/16.
 */
public class MainViewPagerApdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public MainViewPagerApdapter(FragmentManager fm) {
        super(fm);
    }

    public MainViewPagerApdapter(FragmentManager fm, List<Fragment> fragmentList) {
        this(fm);
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


}
