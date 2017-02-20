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
            viewHolder.mTextViewChatUser.setText(model.getText());
        } else {
            viewHolder.mTextViewChat.setText(model.getText());
            viewHolder.mAutoTextViewChatUser.setTextAutoTyping(model.getUser());
            viewHolder.mAutoTextViewChatDate.setTextAutoTyping(model.getTimestamp());
            viewHolder.mAutoTextViewChatDate.setDecryptionSpeed(150);
            viewHolder.mAutoTextViewChatDate.setTypingSpeed(50);
        }


        ViewAnimator.animate(viewHolder.mLayoutChatItems)
                .slideBottom()
                .descelerate()
                .duration(300)
                .start();
        FontsManager.changeFonts(viewHolder.mTextViewChat);
        FontsManager.changeFonts(viewHolder.mTextViewChatUser);


            viewHolder.mTextViewChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewAnimator.animate(viewHolder.mCardViewChat)
                            .slideTop()
                            .duration(200)
                            .andAnimate(viewHolder.mCardViewChatDetail)
                            .slideBottom()
                            .duration(200)
                            .start();
                    viewHolder.mCardViewChatDetail.setVisibility(View.VISIBLE);
                    viewHolder.mCardViewChat.setVisibility(View.GONE);
                }
            });

        viewHolder.mCardViewChatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAnimator.animate(viewHolder.mCardViewChatDetail)
                        .slideTop()
                        .duration(200)
                        .andAnimate(viewHolder.mCardViewChat)
                        .slideBottom()
                        .duration(200)
                        .start();
                viewHolder.mCardViewChatDetail.setVisibility(View.GONE);
                viewHolder.mCardViewChat.setVisibility(View.VISIBLE);
            }
        });

        if (mUid.equals(model.getUid())){
            viewHolder.mCardViewChatUser.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mChatKey = model.getKey();

                    ViewAnimator.animate(viewHolder.mLayoutDeleteChat)
                            .slideRight()
                            .descelerate()
                            .duration(300)
                            .start();

                    ViewAnimator.animate(viewHolder.mCardViewChat)
                            .slideTop()
                            .duration(200)
                            .start();
                    viewHolder.mCardViewChat.setVisibility(View.INVISIBLE);
                    viewHolder.mLayoutDeleteChat.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }

        viewHolder.mFabDeleteChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMessage();
                viewHolder.mCardViewChat.setVisibility(View.VISIBLE);
                viewHolder.mLayoutDeleteChat.setVisibility(View.GONE);
            }
        });

        viewHolder.mFabCancelChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAnimator.animate(viewHolder.mLayoutDeleteChat)
                        .slideRight()
                        .descelerate()
                        .duration(300)
                        .start();

                ViewAnimator.animate(viewHolder.mCardViewChat)
                        .slideBottom()
                        .duration(200)
                        .start();
                viewHolder.mCardViewChat.setVisibility(View.VISIBLE);
                viewHolder.mLayoutDeleteChat.setVisibility(View.INVISIBLE);
                viewHolder.mCardViewChat.setVisibility(View.GONE);
            }
        });



        if (model.getPhotoUrl() == null) {
            viewHolder.mCircleImageViewChat
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            if (mUid.equals(model.getUid())){
                viewHolder.mCardViewChatUser.setVisibility(View.VISIBLE);
                viewHolder.mCardViewChat.setVisibility(View.INVISIBLE);
                viewHolder.mCircleImageViewChat.setVisibility(View.INVISIBLE);

            }else
            Glide.with(context)
                    .load(model.getPhotoUrl())
                    .priority(Priority.NORMAL)
                    .into(viewHolder.mCircleImageViewChat);
            if (!mUid.equals(model.getUid())){
                viewHolder.mCardViewChatUser.setVisibility(View.INVISIBLE);
                viewHolder.mCardViewChat.setVisibility(View.VISIBLE);
                viewHolder.mCircleImageViewChat.setVisibility(View.VISIBLE);
            }
        }
    }

    private void deleteMessage() {
        FirebaseUtil.getChatRef().child(mChatKey).removeValue();
    }

}
