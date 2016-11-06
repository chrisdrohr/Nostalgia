package com.example.gabekeyner.nostalgia;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, Viewholder> {

    private static final String TAG = PostAdapter.class.getSimpleName();
    private Context context;
    int previousPosition = 0;
    private String title, imageURL;
    public ImageView detailImage;


    public PostAdapter(Class<Post> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    public void populateViewHolder(final Viewholder viewHolder, final Post model, int position) {
        //        final Post post =getItem(position);


        viewHolder.mTitle.setText(model.getTitle());
        Glide.with(context).load(model.getImageURL()).thumbnail(0.1f).into(viewHolder.mImageView);

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
                openDetail(model.getTitle(), model.getImageURL());

            }
        });
    }
        private void openDetail(String title, String imageURL) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("imageURL", imageURL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
