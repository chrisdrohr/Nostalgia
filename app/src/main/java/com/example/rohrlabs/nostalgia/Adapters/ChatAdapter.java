package com.example.rohrlabs.nostalgia.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Chat;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.database.Query;
import com.sloop.fonts.FontsManager;


public class ChatAdapter extends FirebaseRecyclerAdapter<Chat, Viewholder>{

    private Context context;
    private String mUid, mChatKey;

    public ChatAdapter(Class<Chat> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(final Viewholder viewHolder, final Chat model, final int position) {
        mUid = FirebaseUtil.getUid();
        if (mUid.equals(model.getUid())) {
            viewHolder.mTextViewCommentUser.setText(model.getText());
        } else {
            viewHolder.commentTextView.setText(model.getText());
            viewHolder.commentAutoTypeTextView.setTextAutoTyping(model.getUser());
            viewHolder.commentTimestampAutoTextView.setTextAutoTyping(model.getTimestamp());
            viewHolder.commentTimestampAutoTextView.setDecryptionSpeed(150);
            viewHolder.commentAutoTypeTextView.setTypingSpeed(50);
        }


        ViewAnimator.animate(viewHolder.layoutCommentItems)
                .slideBottom()
                .descelerate()
                .duration(300)
                .start();
        FontsManager.changeFonts(viewHolder.commentTextView);
        FontsManager.changeFonts(viewHolder.commentNameTextView);


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
            viewHolder.mCardViewCommentUser.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mChatKey = model.getKey();

                    ViewAnimator.animate(viewHolder.layoutDeletComment)
                            .slideRight()
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
                deleteMessage();
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
                viewHolder.mCardViewComment.setVisibility(View.GONE);
            }
        });



        if (model.getPhotoUrl() == null) {
            viewHolder.commentImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            if (mUid.equals(model.getUid())){
                viewHolder.mCardViewCommentUser.setVisibility(View.VISIBLE);
                viewHolder.mCardViewComment.setVisibility(View.INVISIBLE);
                viewHolder.commentImageView.setVisibility(View.INVISIBLE);

            }else
            Glide.with(context)
                    .load(model.getPhotoUrl())
                    .priority(Priority.NORMAL)
                    .into(viewHolder.commentImageView);
            if (!mUid.equals(model.getUid())){
                viewHolder.mCardViewCommentUser.setVisibility(View.INVISIBLE);
                viewHolder.mCardViewComment.setVisibility(View.VISIBLE);
                viewHolder.commentImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void deleteMessage() {
        FirebaseUtil.getChatRef().child(mChatKey).removeValue();
    }

}
