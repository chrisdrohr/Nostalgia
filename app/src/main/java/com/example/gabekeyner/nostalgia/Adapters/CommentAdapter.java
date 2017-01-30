package com.example.gabekeyner.nostalgia.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Comment;
import com.example.gabekeyner.nostalgia.R;
import com.example.gabekeyner.nostalgia.Viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.sloop.fonts.FontsManager;


public class CommentAdapter extends FirebaseRecyclerAdapter<Comment, Viewholder> {

    private Context context;
    private String mUid;
    public static String post_key = PostAdapter.post_key;

    public CommentAdapter(Class<Comment> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(Viewholder viewHolder, final Comment model, final int position) {
        viewHolder.commentTextView.setText(model.getText());
        viewHolder.commentAutoTypeTextView.setTextAutoTyping(model.getUser());
        viewHolder.commentTimestampAutoTextView.setTextAutoTyping(model.getTimestamp());
        viewHolder.commentTimestampAutoTextView.setDecryptionSpeed(150);
        viewHolder.commentAutoTypeTextView.setTypingSpeed(50);
        FontsManager.changeFonts(viewHolder.commentTextView);
        FontsManager.changeFonts(viewHolder.commentNameTextView);
        mUid = FirebaseUtil.getUid();
        if (mUid.equals(model.getUid())){
            viewHolder.commentTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new MaterialDialog.Builder(context)
                            .title("Are you sure you want to delete this?").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            deleteComment();
                        }
                    })
                    .positiveText("Delete")
                    .show();
                    deleteComment();
                    return false;
                }
            });
        }

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

    void deleteComment () {
        FirebaseUtil.getCommentsRef().child(post_key).removeValue();
    }
}
