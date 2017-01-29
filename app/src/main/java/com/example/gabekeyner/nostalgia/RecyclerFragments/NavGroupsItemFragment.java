package com.example.gabekeyner.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.Adapters.NavGroupsAdapter;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.R;
import com.example.gabekeyner.nostalgia.Viewholder;

public class NavGroupsItemFragment extends Fragment {
    private static final String TAG = "NavGroupsItemFragment";
    private RecyclerView mRecyclerView;
    private NavGroupsAdapter mNavGroupsAdapter;
    private LinearLayoutManager mLayoutManager;

    public NavGroupsItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mNavGroupsAdapter = new NavGroupsAdapter(
                Group.class,
                R.layout.fragment_nav_groups_item,
                Viewholder.class,
                FirebaseUtil.getGroupRef(),
                getContext());
        mLayoutManager = new LinearLayoutManager(getContext());
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nav_groups_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewNavGroupslist);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mNavGroupsAdapter);
        return rootView;
    }

}
