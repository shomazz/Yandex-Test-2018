package com.shomazzap.yandex.View;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shomazzap.yandex.PhotosPresenter;
import com.shomazzap.yandex.R;
import com.shomazzap.yandex.Util.Constants;

public class FullScreenPhotoFragment extends DialogFragment {

    private PhotosPresenter presenter;
    private ViewPager viewPager;
    private int currentPosition = 0;

    public static FullScreenPhotoFragment newInstance(PhotosPresenter presenter) {
        FullScreenPhotoFragment fragment = new FullScreenPhotoFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fullscreen_photo, container, false);
        init(view);
        return view;
    }

    public void init(View view) {
        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
        viewPager = (ViewPager) view.findViewById(R.id.photos_view_pager);
        currentPosition = getArguments().getInt(Constants.POSITION);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setCurrentItem(currentPosition);
    }

    public void setPresenter(PhotosPresenter presenter) {
        this.presenter = presenter;
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private final LayoutInflater layoutInflater;
        private RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA);

        public ViewPagerAdapter() {
            layoutInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = layoutInflater
                    .inflate(R.layout.image_view_fullscreen, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            MyRequestListenner requestListenner =
                    new MyRequestListenner(view, progressBar);
            Glide.with(getActivity())
                    .load(presenter.getPhotoLinkMaxQuality(position))
                    .thumbnail(0.5f)
                    .listener(requestListenner)
                    .apply(options)
                    .into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return presenter.getCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class MyRequestListenner implements RequestListener<Drawable> {

        ProgressBar progressBar;
        View view;

        public MyRequestListenner(View view, ProgressBar progressBar) {
            this.progressBar = progressBar;
            this.view = view;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            System.out.println("Exception ! OnlineModel : " + model);
            if (e != null) e.printStackTrace();
            ((TextView)view.findViewById(R.id.error_text_view)).setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                       DataSource dataSource, boolean isFirstResource) {
            progressBar.setVisibility(View.GONE);
            return false;
        }
    }

}
