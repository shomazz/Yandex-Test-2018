package com.shomazzap.yandex.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shomazzap.yandex.PhotoView;
import com.shomazzap.yandex.PhotosPresenter;
import com.shomazzap.yandex.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ImageViewHolder> {

    private Context context;
    private PhotosPresenter presenter;
    private RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)  //Photos will cache to disk before decoding
            .placeholder(R.drawable.preview_error);
    // .error(R.drawable.preview_error);

    private RequestListener requestListener = new RequestListener() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target,
                                    boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target,
                                       DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };

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
                    .listener(requestListener)
                    .apply(options)
                    .into(imageView);
        }
    }
}
