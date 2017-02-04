package com.example.rohrlabs.nostalgia.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.database.Query;

public class AddedUserAdapter extends FirebaseRecyclerAdapter<User, Viewholder> {

    private Context context;
    public static String groupKey = "groupKey";
    public static String userKey = "userKey";

    public AddedUserAdapter(Class<User> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final Viewholder viewHolder, User model, final int position) {

        viewHolder.addedUserAutoTypeTextView.setTextAutoTyping(model.getUserName());
        viewHolder.addedUserAutoTypeTextView.setTypingSpeed(50);

        if (model.getProfilePicture() == null) {
            viewHolder.addedUserImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            Glide.with(context)
                    .load(model.getProfilePicture())
                    .centerCrop()
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.NORMAL)
                    .into(viewHolder.addedUserImageView);
        }
        ViewAnimator.animate(viewHolder.addedUserImageView)
                .bounceIn()
                .duration(500)
                .start();

        viewHolder.addedUserImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) throws IndexOutOfBoundsException {
                if (v != null) {
                    groupKey = GroupFragment.groupKey;
                    userKey = getRef(position).getKey();
                    FirebaseUtil.getGroupMemberRef().child(userKey).removeValue();
                    notifyItemChanged(position);
                    return false;
                }
                else {
                    throw new IndexOutOfBoundsException("Updating...");
                }
            }
        });
    }
}
