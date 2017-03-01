package com.wumart.lib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wumart.lib.common.ArrayUtils;

import java.util.Arrays;
import java.util.List;

/**
 * User: 吕勇
 * Date: 2016-05-05
 * Time: 14:39
 * Description:FragmentStatePagerAdapter基类
 */
public class LBaseFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private String[] mTitles;

    public LBaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    public LBaseFragmentAdapter(FragmentManager fm, Fragment[] fragments, String...titles) {
        super(fm);
        mFragments = Arrays.asList(fragments);
        mTitles = titles;
    }

    public LBaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return ArrayUtils.isEmpty(mFragments) ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ArrayUtils.isEmpty(mFragments) ? "" : mTitles[position];
    }
}
