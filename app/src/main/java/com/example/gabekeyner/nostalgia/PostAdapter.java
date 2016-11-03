package com.example.gabekeyner.nostalgia;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.gabekeyner.nostalgia.FirebaseClasses.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, Viewholder> {

    private static final String TAG = PostAdapter.class.getSimpleName();
    private Context context;

    public PostAdapter(Class<Post> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, Post model, int position) {
    viewHolder.mTitle.setText(model.getTitle());
        Glide.with(context).load(model.getImageURL()).into(viewHolder.mImageView);
    }
}
