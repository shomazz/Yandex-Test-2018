package com.shomazzap.yandex.View;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.shomazzap.yandex.Adapters.FragmentAdapter;
import com.shomazzap.yandex.R;

public class MainActivity extends FragmentActivity {

    private final String logTag = getClass().getSimpleName();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TabItem tabPhotos;
    private TabItem tabOffline;
    private FragmentAdapter fragmentAdapter;
    private ViewPager viewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        tabLayout = findViewById(R.id.tablayout);
        tabPhotos = findViewById(R.id.tab_photos);
        tabOffline = findViewById(R.id.tab_offline);

        viewPager = (ViewPager) findViewById(R.id.fragment_view_pager);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}

