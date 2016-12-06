package com.example.gabekeyner.nostalgia;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class UserAdapter extends FirebaseRecyclerAdapter<User, Viewholder> {

    private Context context;

    public UserAdapter(Class<User> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, User model, int position) {

//        viewHolder.autoTypeTextView.setTextAutoTyping(model.getUserName());
//        viewHolder.autoTypeTextView.setTypingSpeed(20);
//        viewHolder.mUsername.setText(model.getUserName());
        Glide.with(context)
                .load(model.getProfilePicture())
                .thumbnail(0.1f)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.circleImageView);

    }
}
