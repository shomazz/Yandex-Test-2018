package com.shomazzap.yandex.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shomazzap.yandex.Adapters.PhotosAdapter;
import com.shomazzap.yandex.FragmentView;
import com.shomazzap.yandex.PhotosPresenter;
import com.shomazzap.yandex.R;
import com.shomazzap.yandex.Util.Constants;

public class PhotosFragment extends Fragment implements FragmentView, SwipeRefreshLayout.OnRefreshListener {

    private int fragmentType;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PhotosAdapter photosAdapter;
    private PhotosPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String logTag = getClass().getSimpleName();

    public static PhotosFragment newInstance(int fragmentType) {
        Bundle args = new Bundle();
        args.putInt(Constants.FRAGMENT_TYPE, fragmentType);
        PhotosFragment fragment = new PhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentType = getArguments().getInt(Constants.FRAGMENT_TYPE);
        Log.d(logTag, "fragment type = " + fragmentType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_photos, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_photos);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_photos);

        presenter = new PhotosPresenter(this);
        layoutManager = new GridLayoutManager(getContext(), 2);
        photosAdapter = new PhotosAdapter(presenter, view.getContext());


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisiblesItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                if (!presenter.isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                    presenter.loadMoreWalls(null);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photosAdapter);
        if (fragmentType == Constants.ONLINE_FRAGMENT_TYPE)
            presenter.loadMoreWalls(null);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void showMsg(String msg, int lengthType) {
        Toast.makeText(getActivity(), msg, lengthType).show();
    }

    @Override
    public void onDataRecieved() {
        photosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        presenter.loadMoreWalls(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}