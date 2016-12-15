package com.example.gabekeyner.nostalgia;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavDrawerGroupsFragment extends Fragment {

    public static class GroupsViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView navDrawerImageView;
        public TextView navDrawerTextView;

        public GroupsViewHolder(View itemView) {
            super(itemView);
            navDrawerImageView = (CircleImageView) itemView.findViewById(R.id.navDrawerImageView);
            navDrawerTextView = (TextView) itemView.findViewById(R.id.navDrawerTextView);
        }
    }
    public RecyclerView navDrawerRecyclerView;
    public FirebaseRecyclerAdapter<Group, GroupsViewHolder> mFirebaseAdapter;
    public String mUserName, mPhotoUrl, mUid, mGroupName;
    public Context context;
    public LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

//        navDrawerRecyclerView = (RecyclerView) findViewById(R.id.navigationDrawerRecyclerView);
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserName = FirebaseUtil.getUserName();
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mGroupName = FirebaseUtil.getGroupRef().getKey();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Group, GroupsViewHolder>(
                Group.class,
                R.layout.nav_drawer_list_items,
                GroupsViewHolder.class,
                FirebaseUtil.getGroupRef()
        ) {
            @Override
            protected void populateViewHolder(GroupsViewHolder viewHolder, Group model, int position) {
//                Glide.with(NavDrawerGroupsFragment.this)
//                        .load(mPhotoUrl)
//                        .centerCrop()
//                        .thumbnail(0.5f)
//                        .crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .priority(Priority.IMMEDIATE)
//                        .into(viewHolder.navDrawerImageView);
                viewHolder.navDrawerTextView.setText(mGroupName);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_drawer_list_items, container, false);
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Group, GroupsViewHolder>(
                Group.class,
                R.layout.nav_drawer_list_items,
                GroupsViewHolder.class,
                FirebaseUtil.getGroupRef()
        ) {
            @Override
            protected void populateViewHolder(GroupsViewHolder viewHolder, Group model, int position) {
//                Glide.with(NavDrawerGroupsFragment.this)
//                        .load(mPhotoUrl)
//                        .centerCrop()
//                        .thumbnail(0.5f)
//                        .crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .priority(Priority.IMMEDIATE)
//                        .into(viewHolder.navDrawerImageView);
                viewHolder.navDrawerTextView.setText(mGroupName);
            }
        };
        navDrawerRecyclerView = (RecyclerView) view.findViewById(R.id.navigationDrawerRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
//        navDrawerRecyclerView.setItemViewCacheSize(10);

        navDrawerRecyclerView.setLayoutManager(layoutManager);
        navDrawerRecyclerView.setAdapter(mFirebaseAdapter);

        return view;
    }
    public void initView () {

    }
}
