package com.shomazzap.yandex.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shomazzap.yandex.Interfaces.MyFragment;
import com.shomazzap.yandex.R;
import com.shomazzap.yandex.Util.Constants;
import com.shomazzap.yandex.View.PhotosFragment;

import java.util.ArrayList;


public class FragmentAdapter extends FragmentPagerAdapter {

    private final int fragmentCount;
    private final Context context;
    private ArrayList<MyFragment> fragments;

    public FragmentAdapter(FragmentManager fragmentManager, int fragmentsCount,
                           Context context) {
        super(fragmentManager);
        this.fragmentCount = fragmentsCount;
        this.context = context;
        this.fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = PhotosFragment.newInstance(i);
        fragments.add((MyFragment) fragment);
        return fragment;
    }

    public MyFragment getFragment(int position){
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragmentCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == Constants.ONLINE_FRAGMENT_TYPE)
            return context.getResources().getString(R.string.photos_tab_title);
        else return context.getResources().getString(R.string.offline_tab_title);
        // There are only two Fragments are needed in this app
        // that why i used this simple conditional construction
    }
}