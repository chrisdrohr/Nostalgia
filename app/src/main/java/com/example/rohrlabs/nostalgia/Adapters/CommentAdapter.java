package com.example.rohrlabs.nostalgia.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Comment;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.ViewHolders.ViewHolderComment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.database.Query;
import com.sloop.fonts.FontsManager;


public class CommentAdapter extends FirebaseRecyclerAdapter<Comment, ViewHolderComment>{

    private Context context;
    private String mUid, mPostKey, mCommentKey;

    public CommentAdapter(Class<Comment> modelClass, int modelLayout, Class<ViewHolderComment> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final ViewHolderComment viewHolder, final Comment model, final int position) {

        mUid = FirebaseUtil.getUid();
        if (mUid.equals(model.getUid())) {
            viewHolder.mTextViewCommentUser.setText(model.getText());
        } else {
            viewHolder.mTextViewComment.setText(model.getText());
            viewHolder.mAutoTextViewComment.setTextAutoTyping(model.getUser());
            viewHolder.mAutoTextViewCommentTimestamp.setTextAutoTyping(model.getTimestamp());
            viewHolder.mAutoTextViewCommentTimestamp.setDecryptionSpeed(150);
            viewHolder.mAutoTextViewCommentTimestamp.setTypingSpeed(50);
        }

        ViewAnimator.animate(viewHolder.mLayoutCommentItems)
                .slideBottom()
                .descelerate()
                .duration(300)
                .start();

        FontsManager.changeFonts(viewHolder.mTextViewComment);
        FontsManager.changeFonts(viewHolder.mTextViewCommentUser);

            viewHolder.mTextViewComment.setOnClickListener(new View.OnClickListener() {
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
            viewHolder.mCardViewCommentUser.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mPostKey = model.getPostKey();
                    mCommentKey = model.getCommentKey();

                    ViewAnimator.animate(viewHolder.mLayoutCommentDelete)
                            .slideRight()
                            .descelerate()
                            .duration(300)
                            .start();

                    ViewAnimator.animate(viewHolder.mCardViewComment)
                            .slideTop()
                            .duration(200)
                            .start();

                    viewHolder.mCardViewComment.setVisibility(View.INVISIBLE);
                    viewHolder.mLayoutCommentDelete.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }

        viewHolder.mFabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComment();
                viewHolder.mCardViewComment.setVisibility(View.VISIBLE);
                viewHolder.mLayoutCommentDelete.setVisibility(View.GONE);
            }
        });

        viewHolder.mFabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAnimator.animate(viewHolder.mLayoutCommentDelete)
                        .slideRight()
                        .descelerate()
                        .duration(300)
                        .start();

                ViewAnimator.animate(viewHolder.mCardViewComment)
                        .slideBottom()
                        .duration(200)
                        .start();

                viewHolder.mCardViewComment.setVisibility(View.VISIBLE);
                viewHolder.mLayoutCommentDelete.setVisibility(View.INVISIBLE);
                viewHolder.mCardViewComment.setVisibility(View.GONE);
            }
        });

        if (model.getPhotoUrl() == null) {
            viewHolder.mCircleImageViewComment
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            if (mUid.equals(model.getUid())){
                viewHolder.mCardViewCommentUser.setVisibility(View.VISIBLE);
                viewHolder.mCardViewComment.setVisibility(View.INVISIBLE);
                viewHolder.mCircleImageViewComment.setVisibility(View.INVISIBLE);

            }else
            Glide.with(context)
                    .load(model.getPhotoUrl())
                    .priority(Priority.NORMAL)
                    .into(viewHolder.mCircleImageViewComment);
            if (!mUid.equals(model.getUid())){
                viewHolder.mCardViewCommentUser.setVisibility(View.INVISIBLE);
                viewHolder.mCardViewComment.setVisibility(View.VISIBLE);
                viewHolder.mCircleImageViewComment.setVisibility(View.VISIBLE);
            }
        }
    }

    private void deleteComment () {
        FirebaseUtil.getCommentsRef().child(mPostKey).child(mCommentKey).removeValue();
    }

}
