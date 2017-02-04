package com.example.rohrlabs.nostalgia.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Comment;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.database.Query;
import com.sloop.fonts.FontsManager;


public class CommentAdapter extends FirebaseRecyclerAdapter<Comment, Viewholder>{

    private Context context;
    private String mUid, mPostKey, mCommentKey;

    public CommentAdapter(Class<Comment> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final Viewholder viewHolder, final Comment model, final int position) {
        viewHolder.commentTextView.setText(model.getText());
        viewHolder.commentAutoTypeTextView.setTextAutoTyping(model.getUser());
        viewHolder.commentTimestampAutoTextView.setTextAutoTyping(model.getTimestamp());
        viewHolder.commentTimestampAutoTextView.setDecryptionSpeed(150);
        viewHolder.commentAutoTypeTextView.setTypingSpeed(50);

        ViewAnimator.animate(viewHolder.layoutCommentItems)
                .slideBottom()
                .descelerate()
                .duration(300)
                .start();
        FontsManager.changeFonts(viewHolder.commentTextView);
        FontsManager.changeFonts(viewHolder.commentNameTextView);
        mUid = FirebaseUtil.getUid();

            viewHolder.commentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewAnimator.animate(viewHolder.mCardViewComment)
                            .slideTop()
                            .duration(200)
                            .andAnimate(viewHolder.mCardViewCommentDetails)
                            .slideBottom()
                            .duration(200)
                            .start();
                    viewHolder.mCardViewCommentDetails.setVisibility(View.VISIBLE);
                    viewHolder.mCardViewComment.setVisibility(View.GONE);
                }
            });

        viewHolder.mCardViewCommentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAnimator.animate(viewHolder.mCardViewCommentDetails)
                        .slideTop()
                        .duration(200)
                        .andAnimate(viewHolder.mCardViewComment)
                        .slideBottom()
                        .duration(200)
                        .start();
                viewHolder.mCardViewCommentDetails.setVisibility(View.GONE);
                viewHolder.mCardViewComment.setVisibility(View.VISIBLE);
            }
        });

        if (mUid.equals(model.getUid())){
            viewHolder.commentTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mPostKey = model.getPostKey();
                    mCommentKey = model.getCommentKey();

                    ViewAnimator.animate(viewHolder.layoutDeletComment)
                            .slideLeft()
                            .descelerate()
                            .duration(300)
                            .start();

                    ViewAnimator.animate(viewHolder.mCardViewComment)
                            .slideTop()
                            .duration(200)
                            .start();
                    viewHolder.mCardViewComment.setVisibility(View.INVISIBLE);
                    viewHolder.layoutDeletComment.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }

        viewHolder.fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComment();
                viewHolder.mCardViewComment.setVisibility(View.VISIBLE);
                viewHolder.layoutDeletComment.setVisibility(View.GONE);
            }
        });

        viewHolder.fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAnimator.animate(viewHolder.layoutDeletComment)
                        .slideRight()
                        .descelerate()
                        .duration(300)
                        .start();

                ViewAnimator.animate(viewHolder.mCardViewComment)
                        .slideBottom()
                        .duration(200)
                        .start();
                viewHolder.mCardViewComment.setVisibility(View.VISIBLE);
                viewHolder.layoutDeletComment.setVisibility(View.INVISIBLE);
            }
        });



        if (model.getPhotoUrl() == null) {
            viewHolder.commentImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            Glide.with(context)
                    .load(model.getPhotoUrl())
                    .priority(Priority.NORMAL)
                    .into(viewHolder.commentImageView);
        }
    }

    private void deleteComment () {
        FirebaseUtil.getCommentsRef().child(mPostKey).child(mCommentKey).removeValue();
    }

}
