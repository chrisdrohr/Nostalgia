package com.example.rohrlabs.nostalgia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.rohrlabs.nostalgia.Activities.DetailActivity;
import com.example.rohrlabs.nostalgia.AnimationUtil;
import com.example.rohrlabs.nostalgia.Fragments.CommentFragment;
import com.example.rohrlabs.nostalgia.ObjectClasses.Post;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.RecyclerFragments.CommentItemFragment;
import com.example.rohrlabs.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.database.Query;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, Viewholder> {

    private static final String TAG = PostAdapter.class.getSimpleName();
    private Context context;
    private Query mRef;
    int previousPosition = 0;
    public static String post_key = "post_key";

    public PostAdapter(Class<Post> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        this.mRef = ref;
//        ref.orderByKey();
    }

    @Override
    public void populateViewHolder(final Viewholder viewHolder, final Post model, final int position) {
            final String post_key = getRef(position).getKey();
            viewHolder.mTextViewPostTitle.setText(model.getTitle());
            viewHolder.mTextViewPostUserName.setText("-" + model.getUser());
            Glide.with(context)
                    .load(model.getImageURL())
                    .crossFade()
                    .centerCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.mImageViewPost);

//        FOR ANIMATION
        if (position > previousPosition) {
            ViewAnimator.animate(viewHolder.mCardViewPost)
                    .rollIn()
                    .duration(500)
                    .start();
            //We are scrolling down
            AnimationUtil.animate(viewHolder, false);
        } else { //We are scrolling up
            AnimationUtil.animate(viewHolder, true);
        }
        previousPosition = getItemCount();
        final int center = R.anim.rotate_clockwise;
        int lastPosition = -1;

        ViewAnimator.animate(viewHolder.mCardViewPost)
                .newsPaper()
                .descelerate()
                .duration(100)
                .start();
        AnimationUtil.setAnimation(viewHolder.mCardViewPost, previousPosition);

        viewHolder.mImageViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(context, CommentItemFragment.class);
                commentIntent.putExtra("post_key", post_key);
                context.sendBroadcast(commentIntent,"post_key");

                Intent postKeyIntent = new Intent(context, CommentAdapter.class);
                commentIntent.putExtra("post_key", post_key);
                context.sendBroadcast(postKeyIntent,"post_key");

                Intent postKey = new Intent(context, CommentFragment.class);
                postKey.putExtra("post_key", post_key);
                context.sendBroadcast(postKey,"post_key");

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("post_key", post_key);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


}
