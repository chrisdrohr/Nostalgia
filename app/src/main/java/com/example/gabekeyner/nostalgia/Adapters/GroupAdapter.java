package com.example.gabekeyner.nostalgia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.Activities.GroupsActivity;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

public class GroupAdapter extends FirebaseRecyclerAdapter<Group, Viewholder> {

    private String mPhotoUrl, mUsername, mUid;
    private static final String TAG = GroupAdapter.class.getSimpleName();
    private Context context;
    int previousPosition = 0;
    private String title, imageURL, groupKey;
    public ImageView detailImage;
    private ImageView imageTransition, viewerImageView;

    public GroupAdapter(Class<Group> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, Group model, int position) {
        final String groupkey = getRef(position).getKey();
        viewHolder.groupsUsername.setText(model.getGroupName());
//        Glide.with(context)
//                .load(model.())
//                .thumbnail(0.5f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(viewHolder.profilePicture);

        mUsername = FirebaseUtil.getUserName();
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Intent intent = ((GroupsActivity) context).getIntent();
        final String groupKey = intent.getExtras().toString();
        viewHolder.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "adapter", Toast.LENGTH_SHORT).show();
//                User user = new User(
//                        mUsername,
//                        mPhotoUrl,
//                        mUid,
//                        null,
//                        null);
//                FirebaseUtil.getGroupUserRef().push().setValue(user);
            }
        });
    }
}
