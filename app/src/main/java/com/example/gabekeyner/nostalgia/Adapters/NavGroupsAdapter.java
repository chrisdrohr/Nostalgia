package com.example.gabekeyner.nostalgia.Adapters;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class NavGroupsAdapter extends FirebaseRecyclerAdapter<Group,Viewholder> {
private Context context;
    public NavGroupsAdapter(Class<Group> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, Group model, int position) {
        viewHolder.navDrawerTextView.setText(model.getGroupName());
        Glide.with(context)
                .load(model.getGroupPhoto())
                .thumbnail(0.1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.navDrawerImageView);
    }
}
