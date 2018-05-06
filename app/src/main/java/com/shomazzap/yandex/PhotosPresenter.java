package com.shomazzap.yandex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.shomazzap.yandex.Interfaces.MyFragment;
import com.shomazzap.yandex.Interfaces.PhotoView;
import com.shomazzap.yandex.Interfaces.PhotosFragmentView;
import com.shomazzap.yandex.Util.Constants;
import com.shomazzap.yandex.View.FullScreenPhotoFragment;
import com.shomazzap.yandex.View.PhotosFragment;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;

public class PhotosPresenter {

    public boolean isLoading;
    private ArrayList<VKApiPhoto> photos;
    private PhotosFragmentView fragment;
    private String logTag = getClass().getSimpleName();
    private Class currentFragment;

    public PhotosPresenter(PhotosFragmentView photosFragmentView) {
        photos = new ArrayList<>();
        fragment = photosFragmentView;
        currentFragment = PhotosFragment.class;
    }

    public void onBindPhotoView(int i, PhotoView photoView) {
        photoView.setAddress(OnlineModel.getPreivewLink(photos.get(i)));
    }

    public void loadMoreWalls(final Runnable runnable) {
        isLoading = true;
        int offset = photos == null ? 0 : photos.size();
        OnlineModel onlineModel = new OnlineModel();
        onlineModel.loadPhotos(offset, Constants.PHOTOS_COUNT, new OnlineModel.LoadPhotosCallback() {
            @Override
            public void onLoadComplete(@Nullable ArrayList<VKApiPhoto> photos) {
                if (photos != null) addNewPhotos(photos);
                else showErrorToast(R.string.load_error, Toast.LENGTH_SHORT);
                if (runnable != null)runnable.run();
                isLoading = false;
            }
        });
    }

    public void onBackPressed(){
        if (currentFragment == FullScreenPhotoFragment.class)
            onCloseFullScreenView();
        else fragment.showExitDialog();
    }

    public void showErrorToast(@StringRes int id, int lengthType){
        ((MyFragment)fragment).showMsg(id, lengthType);
    }

    private void addNewPhotos(ArrayList<VKApiPhoto> photos){
        this.photos.addAll(photos);
        fragment.onDataRecieved();
    }

    public String getPhotoLinkMaxQuality(int index){
        return OnlineModel.getMaxPhotoQulityLink(photos.get(index));
    }

    public void onPhotoClick(int position){
        fragment.openFullScreenPhotoFragment(position);
        currentFragment = FullScreenPhotoFragment.class;
    }

    public void onCloseFullScreenView(){
        fragment.closeFullScreenPhotoFragment();
        currentFragment = PhotosFragment.class;
    }

    public int getCount() {
        return photos.size();
    }
}