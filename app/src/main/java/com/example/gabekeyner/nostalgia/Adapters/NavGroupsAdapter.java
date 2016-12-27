package com.example.gabekeyner.nostalgia.Adapters;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class NavGroupsAdapter extends FirebaseRecyclerAdapter<Group,Viewholder> {
    private Context context;
    private BroadcastReceiver broadcastReceiver;
    private static String groupKey = "groupKey";

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
                Toast.makeText(context, groupKey, Toast.LENGTH_SHORT).show();
//                broadcastReceiver = new MyBroadcastReceiver();
//                IntentFilter intentFilter = new IntentFilter(groupKey);
//                intentFilter.addAction(groupKey);
//                intentFilter.addCategory("android.intent.category.DEFAULT");
//                intentFilter.hasAction("groupKey");

//                Intent intent = new Intent(groupKey);
            }
        });
    }
}
