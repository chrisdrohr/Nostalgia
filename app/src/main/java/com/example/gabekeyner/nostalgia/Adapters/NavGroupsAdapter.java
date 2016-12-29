package com.example.gabekeyner.nostalgia.Adapters;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gabekeyner.nostalgia.Activities.MainActivity;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class NavGroupsAdapter extends FirebaseRecyclerAdapter<Group,Viewholder> {
    private Context context;
    private BroadcastReceiver broadcastReceiver;
    public static String groupKey = "groupKey";
    public static String groupName;

    public NavGroupsAdapter(Class<Group> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final Viewholder viewHolder, final Group model, final int position) {
        viewHolder.navDrawerTextView.setText(model.getGroupName());
        Glide.with(context)
                .load(model.getGroupPhoto())
                .thumbnail(0.1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.navDrawerImageView);
        viewHolder.navDrawerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupKey = model.getGroupId();
                groupName = model.getGroupName();
//                Toast.makeText(context, "nav" + groupKey, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FirebaseUtil.class);
                intent.putExtra(groupKey, "groupKey");
                context.sendBroadcast(intent);
                notifyDataSetChanged();
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
    }
}
