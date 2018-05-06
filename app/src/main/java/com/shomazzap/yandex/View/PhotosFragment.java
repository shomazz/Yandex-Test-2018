package com.shomazzap.yandex.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shomazzap.yandex.Adapters.PhotosAdapter;
import com.shomazzap.yandex.Interfaces.MyFragment;
import com.shomazzap.yandex.Interfaces.PhotosFragmentView;
import com.shomazzap.yandex.PhotosPresenter;
import com.shomazzap.yandex.R;
import com.shomazzap.yandex.Util.Constants;

public class PhotosFragment extends Fragment implements MyFragment,
        PhotosFragmentView, SwipeRefreshLayout.OnRefreshListener {

    private int fragmentType;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PhotosAdapter photosAdapter;
    private PhotosPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String logTag = getClass().getSimpleName();
    private FullScreenPhotoFragment fullScreenFragment;
    private ProgressBar progressBar;

    public static PhotosFragment newInstance(int fragmentType) {
        Bundle args = new Bundle();
        args.putInt(Constants.FRAGMENT_TYPE, fragmentType);
        PhotosFragment fragment = new PhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showExitDialog() {
            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
            ab.setTitle(getResources().getString(R.string.exit));
            ab.setMessage(getResources().getString(R.string.exit_confirmation_msg));
            ab.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            });
            ab.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            ab.show();
    }

    @Override
    public void closeFullScreenPhotoFragment() {
        if (fullScreenFragment != null) fullScreenFragment.dismiss();
    }

    @Override
    public void openFullScreenPhotoFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION, position);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        fullScreenFragment = FullScreenPhotoFragment.newInstance(presenter);
        fullScreenFragment.setArguments(bundle);
        ft.replace(R.id.fragment_frame, fullScreenFragment);
        ft.commit();
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
        progressBar = ((MainActivity)getActivity()).getToolbar()
                .findViewById(R.id.progress_bar_from_tb);

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
                if (!presenter.isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    progressBar.setVisibility(View.VISIBLE);
                    presenter.loadMoreWalls(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
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
    public PhotosPresenter getPresenter() {
        return presenter;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showMsg(int stringId, int lengthType) {
        Toast.makeText(getActivity(), getActivity().getResources().getString(stringId),
                lengthType).show();
    }

    @Override
    public void onDataRecieved() {
        photosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (fragmentType == Constants.ONLINE_FRAGMENT_TYPE)
            presenter.loadMoreWalls(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1500);
        }
    }
}