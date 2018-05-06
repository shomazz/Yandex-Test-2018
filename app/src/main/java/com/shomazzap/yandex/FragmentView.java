package com.shomazzap.yandex;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.StringRes;

public interface FragmentView { //standard view in MVP pattern

    void showMsg(@StringRes int stringId, @IntRange(from = 0, to = 1) int lengthType);
    // string id - id in string resources
    // length type - type of Toast duration.Can be 0 (Toast.LENGTH_SHORT) or 1 (ToastLENGTH_LONG)

    void onDataRecieved();

    void openFullScreenPhotoFragment(int position);

    Context getContext();

}
