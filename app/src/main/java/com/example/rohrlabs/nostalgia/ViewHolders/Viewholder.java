package com.example.rohrlabs.nostalgia.ViewHolders;


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
import com.example.rohrlabs.nostalgia.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.rohrlabs.nostalgia.R.id.addedUserCircleImageView;

public class Viewholder extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public TextView mTextViewPostTitle;
    public ImageView mImageViewPost;
    public TextView mTextViewPostUserName, mTextViewGroupName;
    public ImageView profilePicture, mImageViewGroup;
    public CardView mCardViewPost, mCardViewDeleteComment, mCardViewCommentDetails, mCardViewComment ,mCardViewCommentUser, mCardViewChat, mCardViewChatUser, mCardViewDeleteChat, mCardViewChatDetail, mCardViewGroup;
    public Context mContext;
    public ImageView detailImageView, userProfileImageView, groupsImageView;
    public TextView detailTitle, userProfileTextView, groupsTextView, mTextViewChat, mTextViewChatUser, mTextViewDeleteChat;
    public EditText mEditText;
    public AutoTypeTextView autoTypeTextView, mAutoTextViewChatUser, mAutoTextViewChatDate;
    public CircleImageView circleImageView;
    public FloatingActionButton fabDelete, fabCancel, mFabDeleteChat, mFabCancelChat;
    public CircleImageView navDrawerImageView, mCircleImageViewGroupMember;
    public TextView navDrawerTextView;
    public RelativeLayout layoutDeletComment, layoutCommentItems, mLayoutDeleteChat, mLayoutChatItems;
    public TextView commentTextView, mTextViewCommentUser;
    public TextView commentNameTextView;
    public CircleImageView commentImageView, addedUserImageView, mCircleImageViewChat;
    public AutoTypeTextView commentAutoTypeTextView, commentTimestampAutoTextView, addedUserAutoTypeTextView, mAutoTypeTextViewGroupMember;


    public Viewholder(View itemView) {
        super(itemView);

        mAutoTypeTextViewGroupMember = (AutoTypeTextView) itemView.findViewById(R.id.autoTypeTextViewGroupMember);
        mCircleImageViewGroupMember = (CircleImageView) itemView.findViewById(R.id.circleImageViewGroupMember);


        mTextViewPostTitle = (TextView) itemView.findViewById(R.id.textViewPostTitle);
        mTextViewPostUserName = (TextView) itemView.findViewById(R.id.textViewPostUserName);
        mImageViewPost = (ImageView) itemView.findViewById(R.id.imageViewPost);
        mImageViewGroup = (ImageView) itemView.findViewById(R.id.imageViewGroup);
        mTextViewGroupName = (TextView) itemView.findViewById(R.id.textViewGroup);
        mCardViewPost = (CardView) itemView.findViewById(R.id.cardViewPost);
        mContext = itemView.getContext();
        detailImageView = (ImageView) itemView.findViewById(R.id.detialView);
        mEditText = (EditText) itemView.findViewById(R.id.groupEditText);
        circleImageView = (CircleImageView) itemView.findViewById(R.id.addedUserCircleImageView);
        autoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.deleteAutoText);
        navDrawerImageView = (CircleImageView) itemView.findViewById(R.id.navDrawerImageView);
        navDrawerTextView = (TextView) itemView.findViewById(R.id.navDrawerTextView);

        addedUserAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.addedUserAutoText);
        addedUserImageView = (CircleImageView) itemView.findViewById(addedUserCircleImageView);
        userProfileImageView = (ImageView) itemView.findViewById(R.id.userProfileImageView);
        userProfileTextView = (TextView) itemView.findViewById(R.id.userProfileTextView);

//        groupsTextView = (TextView) itemView.findViewById(R.id.groupsTextView);
//        groupsImageView = (ImageView) itemView.findViewById(R.id.groupsImageView);

        fabCancel = (FloatingActionButton) itemView.findViewById(R.id.fabCommentCancel);
        fabDelete = (FloatingActionButton) itemView.findViewById(R.id.fabCommentDelete);


        mCardViewGroup = (CardView) itemView.findViewById(R.id.cardViewGroup);

        mImageViewPost = (ImageView) itemView.findViewById(R.id.imageViewPost);

    }


}
