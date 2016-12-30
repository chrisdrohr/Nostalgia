package com.example.gabekeyner.nostalgia;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragankrstic.autotypetextview.AutoTypeTextView;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.gabekeyner.nostalgia.R.id.addedUserCircleImageView;

public class Viewholder extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public TextView mTitle;
    public ImageView mImageView;
    public TextView mUsername, groupsUsername;
    public ImageView profilePicture;
    public CardView mCardView;
    public Context mContext;
    public ImageView detailImageView, userProfileImageView, groupsImageView;
    public TextView detailTitle, userProfileTextView, groupsTextView;
    public EditText mEditText;
    public AutoTypeTextView autoTypeTextView;
    public CircleImageView circleImageView;

    public CircleImageView navDrawerImageView;
    public TextView navDrawerTextView;

    public TextView commentTextView;
    public TextView commentNameTextView;
    public CircleImageView commentImageView, addedUserImageView;
    public AutoTypeTextView commentAutoTypeTextView, commentTimestampAutoTextView, addedUserAutoTypeTextView;


    public Viewholder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.textView);
        mUsername = (TextView) itemView.findViewById(R.id.usernameTextView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        mCardView = (CardView) itemView.findViewById(R.id.cardView);
        mContext = itemView.getContext();
        detailImageView = (ImageView) itemView.findViewById(R.id.detialView);
        detailTitle = (TextView) itemView.findViewById(R.id.commentDetailTitle);
        mEditText = (EditText) itemView.findViewById(R.id.groupEditText);
        circleImageView = (CircleImageView) itemView.findViewById(R.id.addedUserCircleImageView);
        autoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.deleteAutoText);
        navDrawerImageView = (CircleImageView) itemView.findViewById(R.id.navDrawerImageView);
        navDrawerTextView = (TextView) itemView.findViewById(R.id.navDrawerTextView);
        commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
        commentAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.commentUserAutoText);
        commentImageView = (CircleImageView) itemView.findViewById(R.id.commentImageView);
        commentTimestampAutoTextView = (AutoTypeTextView) itemView.findViewById(R.id.dateAutoText);
        addedUserAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.addedUserAutoText);
        addedUserImageView = (CircleImageView) itemView.findViewById(addedUserCircleImageView);
        userProfileImageView = (ImageView) itemView.findViewById(R.id.userProfileImageView);
        userProfileTextView = (TextView) itemView.findViewById(R.id.userProfileTextView);

        groupsTextView = (TextView) itemView.findViewById(R.id.groupsTextView);
        groupsImageView = (ImageView) itemView.findViewById(R.id.groupsImageView);
    }


}
