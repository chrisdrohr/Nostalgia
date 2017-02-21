package com.example.rohrlabs.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.Adapters.PostAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Post;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;

public class MainItemFragment extends Fragment{

    private static final String TAG = "MainItemFragment";
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private Context mContext;
    public static String mGroupkey;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mGroupkey = GroupsAdapter.mGroupKey;

        if (mGroupkey != null) {
            mPostAdapter = new PostAdapter(
                    Post.class,
                    R.layout.fragment_main_item,
                    Viewholder.class,
                    FirebaseUtil.getPostRef(),
                    getActivity());
        } else {
            Toast.makeText(getActivity(), "Choose a Group", Toast.LENGTH_SHORT).show();
        }
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
//        mPostAdapter.notifyDataSetChanged();
//        mGroupkey = GroupsAdapter.mGroupKey;
//        mGroupkey = FirebaseUtil.mGroupKey;
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMainList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPostAdapter);
        if (mPostAdapter != null) {
            mPostAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    int mCount = mPostAdapter.getItemCount();
                    if (mCount > 4 && mCount < 12){
//                    Toast.makeText(getActivity(), String.format("if" + mCount), Toast.LENGTH_SHORT).show();
                        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mPostAdapter);
                    }else if (mCount < 4){
//                    Toast.makeText(getActivity(), String.format("else" + mCount), Toast.LENGTH_SHORT).show();
                        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mPostAdapter);
                    } else if (mCount > 12) {
//                    Toast.makeText(getActivity(), String.format("else if" + mCount), Toast.LENGTH_SHORT).show();
                        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mPostAdapter);
                    }
                }
            });
            mLayoutManager.setItemPrefetchEnabled(false);
        } else {
//            Toast.makeText(getActivity(), "else", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
}

