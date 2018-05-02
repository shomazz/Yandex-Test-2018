package com.shomazzap.yandex.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shomazzap.yandex.R;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;
import java.util.Random;

public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<VKApiPhoto> photos;
    private Context context;

    public PhotosAdapter(ArrayList<VKApiPhoto> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View wallpapperView = inflater.inflate(R.layout.recycler_photos_item, parent, false);
        return new ImageViewHolder(wallpapperView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //TODO: load photo to imView with Glide
    }

    @Override
    public int getItemCount() {
        return 20;
        //return photos.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.photo_item_iview);
            Random rnd = new Random();
            imageView.setBackgroundColor(Color.argb(255, rnd.nextInt(256),
                    rnd.nextInt(256), rnd.nextInt(256)));
        }

    }

}
