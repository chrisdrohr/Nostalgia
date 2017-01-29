package com.example.gabekeyner.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gabekeyner.nostalgia.Adapters.PostAdapter;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Post;
import com.example.gabekeyner.nostalgia.R;
import com.example.gabekeyner.nostalgia.Viewholder;

public class MainItemFragment extends Fragment {

    private static final String TAG = "MainItemFragment";
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPostAdapter = new PostAdapter(Post.class, R.layout.card_view, Viewholder.class, FirebaseUtil.getPostRef(), getContext());
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        mLayoutManager.setItemPrefetchEnabled(false);
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
        mLayoutManager.setItemPrefetchEnabled(false);
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //LAYOUTS & ORIENTATIONS
        switch (id) {
//            case R.id.linearViewVertical:
//                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
//                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
//                mRecyclerView.setLayoutManager(mLinearLayoutManagerVertical);
//                mLinearLayoutManagerVertical.setItemPrefetchEnabled(false);
//                break;
//            case R.id.twoViewVertical:
//                StaggeredGridLayoutManager mStaggered2VerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                mRecyclerView.setLayoutManager(mStaggered2VerticalLayoutManager);
//                mStaggered2VerticalLayoutManager.setItemPrefetchEnabled(false);
//                break;
//            case R.id.staggeredViewVertical:
//                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//                mRecyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
//                mStaggeredVerticalLayoutManager.setItemPrefetchEnabled(false);
//                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}

