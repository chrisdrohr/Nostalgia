package com.example.rohrlabs.nostalgia.Adapters;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.ViewHolders.ViewholderGroupMembers;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class GroupMembersAdapter extends FirebaseRecyclerAdapter<User,ViewholderGroupMembers> {
    private Context context;
    public static String groupKey = "mGroupKey";
    public static String groupName;
    public static String groupPhoto;

    public GroupMembersAdapter(Class<User> modelClass, int modelLayout, Class<ViewholderGroupMembers> viewHolderClass, Context context, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }


    @Override
    protected void populateViewHolder(ViewholderGroupMembers viewHolder, User model, int position) {
        viewHolder.mAutoTypeTextViewGroupMember.setText(model.getUserName());
        Glide.with(context)
                .load(model.getProfilePicture())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.IMMEDIATE)
                .into(viewHolder.mCircleImageViewGroupMember);
    }
}
