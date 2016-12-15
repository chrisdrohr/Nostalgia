package com.example.gabekeyner.nostalgia;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainItemFragment extends Fragment {

    private static final String TAG = "MainItemFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPostAdapter = new PostAdapter(Post.class, R.layout.card_view, Viewholder.class, FirebaseUtil.getPostRef(), getContext());
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setItemPrefetchEnabled(false);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_item_list, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMainList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPostAdapter);
        return rootView;
    }
}

