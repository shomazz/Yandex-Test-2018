package com.shomazzap.yandex;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.shomazzap.yandex.Util.Constants;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;

import org.json.JSONObject;

import java.util.ArrayList;

public class OnlineModel {

    private final String logTag = getClass().getSimpleName();

    public static String getPreivewLink(VKApiPhoto photo){
        String link = photo.photo_807;  //links arranged by priority of photo's quality
        if (link.equals("")) link = photo.photo_604;
        if (link.equals("")) link = photo.photo_1280;
        return link;
    }

    public static String getMaxPhotoQulityLink(VKApiPhoto photo){
        String link = photo.photo_2560;  //links arranged by priority of photo's quality
        if (link.equals("")) link = photo.photo_1280;
        if (link.equals("")) link = photo.photo_807;
        if (link.equals("")) link = photo.photo_604;
        return link;
    }

    interface LoadPhotosCallback {
        void onLoadComplete(@Nullable ArrayList<VKApiPhoto> photos);
    }

    public void loadPhotos(int offset, int count, LoadPhotosCallback callback){
        LoadPhotosTask task = new LoadPhotosTask(offset, count, callback);
        task.execute();
    }

    class LoadPhotosTask extends AsyncTask<Void, Void, Void> {

        private final LoadPhotosCallback callback;
        private final int offset;
        private final int count;
        private Exception exception;
        private VKError vkError;
        private ArrayList<VKApiPhoto> photos;

        LoadPhotosTask(int offset, int count, LoadPhotosCallback callback) {
            this.offset = offset;   // offset required to select a specific subset of photos
            this.count = count;
            this.callback = callback;
            this.photos = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            VKRequest photosRequest = new VKRequest("photos.get", VKParameters.from(
                    VKApiConst.OWNER_ID, Constants.COMMUNITY_ID,
                    VKApiConst.ALBUM_ID, Constants.ALBUM_ID,
                    VKApiConst.COUNT, count,
                    VKApiConst.OFFSET, offset));
            photosRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    try {
                        Log.d(logTag, response.json.toString());
                        //albumSize = response.json.getJSONObject("response").getInt("count");
                        for (int i = 0; i < count; i++)
                            photos.add(new VKApiPhoto((JSONObject) response.json.getJSONObject("response")
                                    .getJSONArray("items").get(i)));
                    } catch (Exception e) {
                        exception = e;
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VKError error) {
                    vkError = error;
                    Log.e(logTag, error.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            if (callback != null) callback.onLoadComplete(photos);
        }
    }
}
