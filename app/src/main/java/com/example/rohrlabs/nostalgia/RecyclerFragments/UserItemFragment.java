package com.example.rohrlabs.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rohrlabs.nostalgia.Adapters.UserAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;

public class UserItemFragment extends Fragment {

    private static final String TAG = "UserItemFragment";
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private GridLayoutManager mLayoutManager;
    private ImageView userProfileImageView;
    private String mUsername, mPhotoUrl, mUid, groupName, groupId, groupKey;

    public UserItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mUserAdapter = new UserAdapter(User.class, R.layout.fragment_user_item, Viewholder.class, FirebaseUtil.getUserRef(), getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewUserList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mUserAdapter);
        return rootView;
    }


}
