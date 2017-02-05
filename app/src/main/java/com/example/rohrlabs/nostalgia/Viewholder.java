package com.example.rohrlabs.nostalgia;


import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dragankrstic.autotypetextview.AutoTypeTextView;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.rohrlabs.nostalgia.R.id.addedUserCircleImageView;

public class Viewholder extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public TextView mTitle;
    public ImageView mImageView;
    public TextView mUsername, groupsUsername;
    public ImageView profilePicture;
    public CardView mCardView, mCardViewDeleteComment, mCardViewCommentDetails, mCardViewComment ,mCardViewCommentUser;
    public Context mContext;
    public ImageView detailImageView, userProfileImageView, groupsImageView;
    public TextView detailTitle, userProfileTextView, groupsTextView;
    public EditText mEditText;
    public AutoTypeTextView autoTypeTextView;
    public CircleImageView circleImageView;
    public FloatingActionButton fabDelete, fabCancel;
    public CircleImageView navDrawerImageView, mCircleImageViewGroupMember;
    public TextView navDrawerTextView;
    public RelativeLayout layoutDeletComment, layoutCommentItems;
    public TextView commentTextView, mTextViewCommentUser;
    public TextView commentNameTextView;
    public CircleImageView commentImageView, addedUserImageView;
    public AutoTypeTextView commentAutoTypeTextView, commentTimestampAutoTextView, addedUserAutoTypeTextView, mAutoTypeTextViewGroupMember;


    public Viewholder(View itemView) {
        super(itemView);

        mAutoTypeTextViewGroupMember = (AutoTypeTextView) itemView.findViewById(R.id.autoTypeTextViewGroupMember);
        mCircleImageViewGroupMember = (CircleImageView) itemView.findViewById(R.id.circleImageViewGroupMember);


        mTitle = (TextView) itemView.findViewById(R.id.textView);
        mUsername = (TextView) itemView.findViewById(R.id.usernameTextView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        mCardView = (CardView) itemView.findViewById(R.id.cardView);
        mContext = itemView.getContext();
        detailImageView = (ImageView) itemView.findViewById(R.id.detialView);
//        detailTitle = (TextView) itemView.findViewById(R.id.commentDetailTitle);
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

        fabCancel = (FloatingActionButton) itemView.findViewById(R.id.fabCancel);
        fabDelete = (FloatingActionButton) itemView.findViewById(R.id.fabDelete);

        mCardViewDeleteComment = (CardView) itemView.findViewById(R.id.cardView_deleteComment);
        mCardViewCommentDetails = (CardView) itemView.findViewById(R.id.commentDetailCardView);
        mCardViewComment = (CardView) itemView.findViewById(R.id.commentCardView);
        mCardViewCommentUser = (CardView) itemView.findViewById(R.id.commentCardViewUser);

        mTextViewCommentUser = (TextView) itemView.findViewById(R.id.commentTextViewUser);


        layoutDeletComment = (RelativeLayout) itemView.findViewById(R.id.layout_deleteComment);
        layoutCommentItems = (RelativeLayout) itemView.findViewById(R.id.layoutCommentItems);

    }


}
