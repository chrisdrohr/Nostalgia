package com.example.rohrlabs.nostalgia.Adapters;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.rohrlabs.nostalgia.Activities.MainActivity;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Group;
import com.example.rohrlabs.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class GroupsAdapter extends FirebaseRecyclerAdapter<Group,Viewholder> {
    private Context context;
    private BroadcastReceiver broadcastReceiver;
    public static String mGroupKey;
    public static String groupName;
    public static String groupPhoto;

    public GroupsAdapter(Class<Group> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final Viewholder viewHolder, final Group model, final int position) {
        viewHolder.mTextViewGroupName.setText(model.getGroupName());
        Glide.with(context)
                .load(model.getGroupPhoto())
                .crossFade()
                .centerCrop()
                .priority(Priority.IMMEDIATE)
                .into(viewHolder.mImageViewGroup);
        viewHolder.mImageViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGroupKey = model.getGroupId();
                groupName = model.getGroupName();
                groupPhoto = model.getGroupPhoto();
                Intent intent = new Intent(context, FirebaseUtil.class);
                intent.putExtra(mGroupKey, "mGroupKey");
                context.sendBroadcast(intent);
                notifyDataSetChanged();

//                Intent intentPost = new Intent(context, MainItemFragment.class);
//                intentPost.putExtra(mGroupKey, "mGroupKey");
//                context.sendBroadcast(intentPost);
//                notifyDataSetChanged();
//
//                Toast.makeText(context, mGroupKey + " adapter", Toast.LENGTH_SHORT).show();

                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
    }
}
