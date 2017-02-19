package com.example.rohrlabs.nostalgia.RecyclerFragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Group;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;

public class GroupsItemFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "GroupsItemFragment";
    private RecyclerView mRecyclerView;
    private GroupsAdapter mGroupsAdapter;

    public GroupsItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mGroupsAdapter = new GroupsAdapter(
                Group.class,
                R.layout.group_card_view,
                Viewholder.class,
                FirebaseUtil.getGroupRef(),
                getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_groups_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewGroupsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mGroupsAdapter);
        return rootView;
    }

}
