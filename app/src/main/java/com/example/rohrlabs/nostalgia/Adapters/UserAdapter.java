package com.example.rohrlabs.nostalgia.Adapters;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Group;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.ViewHolders.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import static com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment.mGroupKey;

public class UserAdapter extends FirebaseRecyclerAdapter<User, Viewholder> {

    private Context context;
    public static String groupKey = "mGroupKey";
    public static String userKey = "userKey";
    private String mUsername, mPhotoUrl, mUid, mGroupName, mGroupPhoto, mGroupCreator;
    private DatabaseReference databaseReference, mRef;


    public UserAdapter(Class<User> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, final User model, int position) {

            viewHolder.userProfileTextView.setText(model.getUserName());
        Glide.with(context)
                .load(model.getProfilePicture())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.userProfileImageView);

        viewHolder.userProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUid = model.getUid();
                mUsername = model.getUserName();
                mPhotoUrl = model.getProfilePicture();
                groupKey = mGroupKey;
                mGroupCreator = GroupFragment.mGroupCreator;
                mGroupName = GroupFragment.mGroupName;
                mGroupPhoto = GroupFragment.mGroupPhoto;

//                databaseReference = getGroupRef();
//                DatabaseReference ref = databaseReference.child(groupKey).child("members").child(mUid);

                User user = new User(
                        mUsername,
                        mPhotoUrl,
                        mUid,
                        groupKey,
                        null);
//                ref.setValue("true");
//                ref.setValue(user);
//                getBaseRef().child("members").child(mUid).setValue("true");

                final Group group = new Group(
                        mGroupCreator,
                        mGroupName,
                        mGroupPhoto,
                        mGroupKey);
//                ref.setValue(group);
                mRef = FirebaseUtil.getGroupRef().child(groupKey).child("members").child(model.getUid());
                databaseReference = FirebaseUtil.getAddUserRef().child(model.getUid()).child("groups").child(mGroupKey);
                mRef.setValue(user);
                databaseReference.setValue(group);
            }
        });
    }
}
