package com.shomazzap.yandex.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shomazzap.yandex.Adapters.PhotosAdapter;
import com.shomazzap.yandex.R;

public class PhotosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PhotosAdapter photosAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_photos, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_photos);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        photosAdapter = new PhotosAdapter(null, view.getContext());
        recyclerView.setAdapter(photosAdapter);
    }
}