package com.example.rohrlabs.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rohrlabs.nostalgia.Adapters.GroupMembersAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;

public class GroupMembersFragment extends Fragment {

    private static final String TAG = "UserItemFragment";
    private RecyclerView mRecyclerView;
    private GroupMembersAdapter mGroupMembersAdapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private ImageView userProfileImageView;
    private String mUsername, mPhotoUrl, mUid, groupName, groupId, groupKey;

    public GroupMembersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mGroupMembersAdapter = new GroupMembersAdapter(
                User.class,
                R.layout.fragment_group_member_item,
                Viewholder.class,
                getContext(),
                FirebaseUtil.getGroupMemberRef());
        mLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
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


}
