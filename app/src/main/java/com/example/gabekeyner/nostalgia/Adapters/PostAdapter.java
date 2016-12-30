package com.example.gabekeyner.nostalgia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gabekeyner.nostalgia.Activities.DetailActivity;
import com.example.gabekeyner.nostalgia.AnimationUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Post;
import com.example.gabekeyner.nostalgia.R;
import com.example.gabekeyner.nostalgia.RecyclerFragments.CommentItemFragment;
import com.example.gabekeyner.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, Viewholder> {

    private static final String TAG = PostAdapter.class.getSimpleName();
    private Context context;
    int previousPosition = 0;

    public PostAdapter(Class<Post> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    public void populateViewHolder(final Viewholder viewHolder, final Post model, final int position) {
        final String post_key = getRef(position).getKey();
        viewHolder.mTitle.setText(model.getTitle());
        viewHolder.mUsername.setText("-" + model.getUser());
        Glide.with(context)
                .load(model.getImageURL())
                .thumbnail(0.1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.mImageView);

//        FOR ANIMATION
        if (position > previousPosition) {
            //We are scrolling down
            AnimationUtil.animate(viewHolder, false);
        } else { //We are scrolling up
            AnimationUtil.animate(viewHolder, true);
        }
        previousPosition = getItemCount();
        final int center = R.anim.rotate_clockwise;
        int lastPosition = -1;
//        AnimationUtil.setAnimation(viewHolder.mCardView, previousPosition);

        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewAnimator.animate(viewHolder.mCardView)
//                        .bounce()
//                        .duration(20)
//                        .start();
                Intent commentIntent = new Intent(context, CommentItemFragment.class);
                commentIntent.putExtra("post_key", post_key);
                context.sendBroadcast(commentIntent,"post_key");

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("post_key", post_key);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//                Toast.makeText(context, "post adapter" + post_key, Toast.LENGTH_SHORT).show();
//                viewHolder.mImageView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },1);
            }
        });
    }

}
