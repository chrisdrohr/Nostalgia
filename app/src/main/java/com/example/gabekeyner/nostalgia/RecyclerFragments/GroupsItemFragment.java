package com.example.gabekeyner.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gabekeyner.nostalgia.Adapters.GroupsAdapter;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.R;
import com.example.gabekeyner.nostalgia.Viewholder;

public class GroupsItemFragment extends Fragment {
    private static final String TAG = "GroupsItemFragment";
    private RecyclerView mRecyclerView;
    private GroupsAdapter mGroupsAdapter;
    private StaggeredGridLayoutManager mLayoutManager;

    public GroupsItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mGroupsAdapter = new GroupsAdapter(Group.class, R.layout.fragment_groups_item, Viewholder.class, FirebaseUtil.getGroupRef(), getContext());
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_groups_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewGroupsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mGroupsAdapter);
        mLayoutManager.setItemPrefetchEnabled(false);
        return rootView;
    }

}
