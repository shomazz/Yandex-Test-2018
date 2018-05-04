package com.shomazzap.yandex;

import android.support.annotation.IntRange;

public interface FragmentView { //standard view in MVP pattern

    void showMsg(String msg, @IntRange(from = 0, to = 1) int lengthType);
    // length type - type of Toast duration.Can be 0 (Toast.LENGTH_SHORT) or 1 (ToastLENGTH_LONG)

    void onDataRecieved();

}
