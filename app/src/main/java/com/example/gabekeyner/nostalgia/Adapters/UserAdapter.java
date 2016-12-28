package com.example.gabekeyner.nostalgia.Adapters;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gabekeyner.nostalgia.DialogFragments.GroupFragment;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.User;
import com.example.gabekeyner.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import static com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil.getGroupMemberRef;

public class UserAdapter extends FirebaseRecyclerAdapter<User, Viewholder> {

    private Context context;
    public static String groupKey = "groupKey";
    public static String userKey = "userKey";
    private String mUsername, mPhotoUrl, mUid, groupName;
    private DatabaseReference databaseReference;


    public UserAdapter(Class<User> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, User model, int position) {

            viewHolder.userProfileTextView.setText(model.getUserName());
        Glide.with(context)
                .load(model.getProfilePicture())
                .thumbnail(0.1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.userProfileImageView);

        viewHolder.userProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mUsername = FirebaseUtil.getUserName();
                mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
                groupKey = GroupFragment.groupKey;
                databaseReference = getGroupMemberRef();
                DatabaseReference ref = databaseReference.push();

                User user = new User(
                        mUsername,
                        mPhotoUrl,
                        mUid,
                        groupKey,
                        null);
                ref.setValue(user);
                userKey = ref.getKey();
//                getGroupMemberRef().child(groupKey).push().setValue(user);
            }
        });
    }
}
