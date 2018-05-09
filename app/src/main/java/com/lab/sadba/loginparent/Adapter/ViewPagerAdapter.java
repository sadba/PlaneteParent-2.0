package com.lab.sadba.loginparent.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentLists = new ArrayList<>();
    private List<String>   mFragmenTitle  = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private Map<Integer, String> mFragmentTags;
    private Bundle fragmentBundle;
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Bundle bundle, Context context) {
        super(fm);
        fragmentBundle = bundle;
        mFragmentManager = fm;
        mFragmentTags  = new HashMap<Integer, String>();
        mContext       = context;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        fragment.setArguments(fragmentBundle);
        return this.mFragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentLists.size();
    }

    public void AddFragment(Fragment fragment, String title){
        mFragmentLists.add(fragment);
        mFragmenTitle.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position){
        return mFragmenTitle.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment){
            Fragment f = (Fragment) object;
            String tag = f.getTag();
        }

        return object;
    }

    public Fragment getFragmentAt(int position){
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }
}
