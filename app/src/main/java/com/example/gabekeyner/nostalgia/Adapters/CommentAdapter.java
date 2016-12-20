package com.example.gabekeyner.nostalgia.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.gabekeyner.nostalgia.ObjectClasses.Comment;
import com.example.gabekeyner.nostalgia.R;
import com.example.gabekeyner.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.sloop.fonts.FontsManager;


public class CommentAdapter extends FirebaseRecyclerAdapter<Comment, Viewholder> {

    private Context context;

    public CommentAdapter(Class<Comment> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, Comment model, int position) {
        viewHolder.commentTextView.setText(model.getText());
        viewHolder.commentAutoTypeTextView.setTextAutoTyping(model.getUser());
        viewHolder.commentTimestampAutoTextView.setTextAutoTyping(model.getTimestamp());
        viewHolder.commentTimestampAutoTextView.setDecryptionSpeed(150);
        viewHolder.commentAutoTypeTextView.setTypingSpeed(50);
        FontsManager.changeFonts(viewHolder.commentTextView);
        FontsManager.changeFonts(viewHolder.commentNameTextView);

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
}
