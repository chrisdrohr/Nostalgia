package com.example.rohrlabs.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rohrlabs.nostalgia.Adapters.GroupMembersAdapter;
import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.ViewHolders.ViewholderGroupMembers;

public class GroupMembersListFragment extends Fragment {

    private static final String TAG = "GroupMembersListFragment";
    private RecyclerView mRecyclerView;
    private GroupMembersAdapter mGroupMembersAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private String mGroupKey;

    public GroupMembersListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mGroupKey = GroupsAdapter.mGroupKey;
        mGroupKey = GroupFragment.mGroupKey;
        if (mGroupKey != null && mGroupKey != "mGroupKey") {
            mGroupMembersAdapter = new GroupMembersAdapter(
                    User.class,
                    R.layout.fragment_group_member_item,
                    ViewholderGroupMembers.class,
                    getActivity(),
                    FirebaseUtil.getGroupRef().child(mGroupKey).child("members"));
        }
        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_member_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewGroupMemberList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mGroupMembersAdapter);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGroupMembersAdapter != null) {
            mGroupMembersAdapter.cleanup();
        }
    }
}
