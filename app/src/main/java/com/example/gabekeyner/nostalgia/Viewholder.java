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

public class Viewholder extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public TextView mTitle;
    public ImageView mImageView;
    public TextView mUsername, groupsUsername;
    public ImageView profilePicture;
    public CardView mCardView;
    public Context mContext;
    public ImageView detailImageView;
    public TextView detailTitle;
    public EditText mEditText;
    public AutoTypeTextView autoTypeTextView;
    public CircleImageView circleImageView;

    public CircleImageView navDrawerImageView;
    public TextView navDrawerTextView;

    public Viewholder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.textView);
        mUsername = (TextView) itemView.findViewById(R.id.usernameTextView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        mCardView = (CardView) itemView.findViewById(R.id.cardView);
        profilePicture = (ImageView) itemView.findViewById(R.id.imageViewGroup);
        groupsUsername = (TextView) itemView.findViewById(R.id.textViewGroup);
        mContext = itemView.getContext();
        detailImageView = (ImageView) itemView.findViewById(R.id.detialView);
        detailTitle = (TextView) itemView.findViewById(R.id.commentDetailTitle);
        mEditText = (EditText) itemView.findViewById(R.id.groupEditText);
        circleImageView = (CircleImageView) itemView.findViewById(R.id.addedUserCircleImageView);
        autoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.userAutoText);

        navDrawerImageView = (CircleImageView) itemView.findViewById(R.id.navDrawerImageView);
        navDrawerTextView = (TextView) itemView.findViewById(R.id.navDrawerTextView);
    }


}
