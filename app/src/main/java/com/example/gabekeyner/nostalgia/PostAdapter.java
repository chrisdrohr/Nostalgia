package com.example.gabekeyner.nostalgia;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
    public void populateViewHolder(final Viewholder viewHolder, final Post model, int position) {
        final String post_key = getRef(position).getKey();
        viewHolder.mTitle.setText(model.getTitle());
        viewHolder.mUsername.setText("-" + model.getUser());
        Glide.with(context)
                .load(model.getImageURL())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.mImageView);



        //FOR ANIMATION
        if (position > previousPosition) {
            //We are scrolling down
            AnimationUtil.animate(viewHolder, true);
        } else { //We are scrolling up
            AnimationUtil.animate(viewHolder, false);
        }
        previousPosition = position;
        int lastPosition = -1;


        AnimationUtil.setScaleAnimation(viewHolder.mImageView);
        AnimationUtil.setFadeAnimation(viewHolder.mTitle);
        AnimationUtil.setAnimation(viewHolder.mImageView, lastPosition);

        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("uid", post_key);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

}
