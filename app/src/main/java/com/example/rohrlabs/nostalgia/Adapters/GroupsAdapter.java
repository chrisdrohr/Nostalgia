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
import com.example.rohrlabs.nostalgia.ViewHolders.ViewholderGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class GroupsAdapter extends FirebaseRecyclerAdapter<Group,ViewholderGroup> {
    private Context context;
    private BroadcastReceiver broadcastReceiver;
    private Query mQuery;
    private DatabaseReference mDatabaseRef, mRef;
    public static String mGroupKey = "mGroupKey";
    public static String groupName;
    public static String groupPhoto;
    private String mKey;

    public GroupsAdapter(Class<Group> modelClass, int modelLayout, Class<ViewholderGroup> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        this.mQuery = ref.orderByKey();
    }

    @Override
    protected void populateViewHolder(final ViewholderGroup viewHolder, final Group model, final int position) {
        mKey = getRef(position).getKey();

                    viewHolder.mCardViewGroup.setVisibility(View.VISIBLE);
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
                context.startActivity(new Intent(context, MainActivity.class));


            }
        });
    }
}
