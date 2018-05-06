package com.shomazzap.yandex.Interfaces;

public interface PhotosFragmentView {

    void onDataRecieved();

    void openFullScreenPhotoFragment(int position);

    void closeFullScreenPhotoFragment();

    void showExitDialog();

}
