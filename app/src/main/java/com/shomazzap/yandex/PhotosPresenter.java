package com.shomazzap.yandex;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.shomazzap.yandex.Util.Constants;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;

public class PhotosPresenter {

    public boolean isLoading;
    private ArrayList<VKApiPhoto> photos;
    private FragmentView fragment;
    private String logTag = getClass().getSimpleName();

    public PhotosPresenter(FragmentView fragmentView) {
        photos = new ArrayList<>();
        fragment = fragmentView;
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

    public void showErrorToast(@StringRes int id, int lengthType){
        fragment.showMsg(id, lengthType);
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
    }

    public int getCount() {
        return photos.size();
    }

}