package com.example.rohrlabs.nostalgia.Adapters;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rohrlabs.nostalgia.Activities.MainActivity;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Group;
import com.example.rohrlabs.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class NavGroupsAdapter extends FirebaseRecyclerAdapter<Group,Viewholder> {
    private Context context;
    private BroadcastReceiver broadcastReceiver;
    private SharedPreferences sharedPreferences;
    public static String groupKey = "mGroupKey";
    public static String groupName;
    public static String groupPhoto;

    public NavGroupsAdapter(Class<Group> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final Viewholder viewHolder, final Group model, final int position) {
        viewHolder.navDrawerTextView.setText(model.getGroupName());
        Glide.with(context)
                .load(model.getGroupPhoto())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.IMMEDIATE)
                .into(viewHolder.navDrawerImageView);
        viewHolder.navDrawerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupKey = model.getGroupId();
                groupName = model.getGroupName();
                groupPhoto = model.getGroupPhoto();
                Intent intent = new Intent(context, FirebaseUtil.class);
                intent.putExtra(groupKey, "mGroupKey");
                context.sendBroadcast(intent);
                notifyDataSetChanged();
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
    }
}
