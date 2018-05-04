package com.shomazzap.yandex;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.shomazzap.yandex.Util.Constants;
import com.shomazzap.yandex.View.PhotosFragment;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;

public class PhotosPresenter {

    private ArrayList<VKApiPhoto> photos;
    private FragmentView fragment;
    private String logTag = getClass().getSimpleName();

    public PhotosPresenter(FragmentView fragmentView) {
        photos = new ArrayList<>();
        fragment = fragmentView;
    }

    public void onBindPhotoView(int i, PhotoView photoView) {
        photoView.setAddress(photos.get(i).photo_807);
    }

    public void loadMoreWalls() {
        int offset = photos == null ? 0 : photos.size();
        Model model = new Model();
        model.loadPhotos(offset, Constants.PHOTOS_COUNT, new Model.LoadPhotosCallback() {
            @Override
            public void onLoadComplete(@Nullable ArrayList<VKApiPhoto> photos) {
                if (photos != null) addNewPhotos(photos);
                else showErrorToast(Constants.PHOTOS_LOAD_ERROR, Toast.LENGTH_SHORT);
            }
        });
    }

    private void showErrorToast(String msg, int lengthType){
        fragment.showMsg(msg, lengthType);
    }

    private void addNewPhotos(ArrayList<VKApiPhoto> photos){
        this.photos.addAll(photos);
        fragment.onDataRecieved();
    }

    public int getCount() {
        return photos.size();
    }

}