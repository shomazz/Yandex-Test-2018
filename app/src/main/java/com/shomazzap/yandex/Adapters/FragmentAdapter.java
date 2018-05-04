package com.shomazzap.yandex.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shomazzap.yandex.View.PhotosFragment;


public class FragmentAdapter extends FragmentPagerAdapter {

    private final int fragmentCount;

    public FragmentAdapter(FragmentManager fragmentManager, int fragmentsCount) {
        super(fragmentManager);
        this.fragmentCount = fragmentsCount;
    }

    @Override
    public Fragment getItem(int i) {
        return PhotosFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return fragmentCount;
    }
}