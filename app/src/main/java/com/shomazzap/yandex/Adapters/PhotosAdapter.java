package com.shomazzap.yandex.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shomazzap.yandex.PhotoView;
import com.shomazzap.yandex.PhotosPresenter;
import com.shomazzap.yandex.R;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.ArrayList;
import java.util.Random;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ImageViewHolder> {

    private Context context;
    private PhotosPresenter presenter;
    private RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)  //Photos will cache to disk before decoding
            .placeholder(R.drawable.vk_clear_shape);

    public PhotosAdapter(PhotosPresenter presenter, Context context) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public PhotosAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View wallpapperView = inflater.inflate(R.layout.recycler_photos_item, parent, false);
        return new ImageViewHolder(wallpapperView);
    }

    @Override
    public void onBindViewHolder(PhotosAdapter.ImageViewHolder holder, int position) {
        presenter.onBindPhotoView(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements PhotoView {
        private ImageView imageView;

        private ImageViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.photo_item_iview);
        }

        @Override
        public void setAddress(String path) {
            Glide.with(context)
                    .load(path)
                    .transition(withCrossFade())
                    .thumbnail(0.5f)
                    //.listener(requestListener) TODO: add listener to catch errors
                    .apply(options)
                    .into(imageView);
        }

    }

}
