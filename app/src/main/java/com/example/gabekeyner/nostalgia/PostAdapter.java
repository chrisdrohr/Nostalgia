package com.example.gabekeyner.nostalgia;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.database.Query;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, Viewholder> {

    private static final String TAG = PostAdapter.class.getSimpleName();
    private Context context;
    int previousPosition = 0;
    private String title, imageURL;
    public ImageView detailImage;
    private ImageView imageTransition, viewerImageView;

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
                ViewAnimator.animate(viewHolder.mCardView)
                        .tada()
                        .duration(50)
                        .start();
                viewHolder.mImageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("post_key", post_key);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                },10);

            }
        });
    }

}
