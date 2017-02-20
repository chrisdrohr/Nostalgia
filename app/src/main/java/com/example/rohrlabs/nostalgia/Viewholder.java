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

    public TextView mTextViewPostTitle;
    public ImageView mImageViewPost;
    public TextView mTextViewPostUserName, mTextViewGroupName;
    public ImageView profilePicture, mImageViewGroup;
    public CardView mCardViewPost, mCardViewDeleteComment, mCardViewCommentDetails, mCardViewComment ,mCardViewCommentUser, mCardViewChat, mCardViewChatUser, mCardViewDeleteChat, mCardViewChatDetail;
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


        mTextViewPostTitle = (TextView) itemView.findViewById(R.id.textViewPost);
        mTextViewPostUserName = (TextView) itemView.findViewById(R.id.usernameTextViewPost);
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
        commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
        commentAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.commentUserAutoText);
        commentImageView = (CircleImageView) itemView.findViewById(R.id.commentImageView);
        commentTimestampAutoTextView = (AutoTypeTextView) itemView.findViewById(R.id.dateAutoText);
        addedUserAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.addedUserAutoText);
        addedUserImageView = (CircleImageView) itemView.findViewById(addedUserCircleImageView);
        userProfileImageView = (ImageView) itemView.findViewById(R.id.userProfileImageView);
        userProfileTextView = (TextView) itemView.findViewById(R.id.userProfileTextView);

//        groupsTextView = (TextView) itemView.findViewById(R.id.groupsTextView);
//        groupsImageView = (ImageView) itemView.findViewById(R.id.groupsImageView);

        fabCancel = (FloatingActionButton) itemView.findViewById(R.id.fabCancel);
        fabDelete = (FloatingActionButton) itemView.findViewById(R.id.fabDelete);

        mCardViewDeleteComment = (CardView) itemView.findViewById(R.id.cardView_deleteComment);
        mCardViewCommentDetails = (CardView) itemView.findViewById(R.id.commentDetailCardView);
        mCardViewComment = (CardView) itemView.findViewById(R.id.commentCardView);
        mCardViewCommentUser = (CardView) itemView.findViewById(R.id.commentCardViewUser);

        mTextViewCommentUser = (TextView) itemView.findViewById(R.id.commentTextViewUser);


        layoutDeletComment = (RelativeLayout) itemView.findViewById(R.id.layout_deleteComment);
        layoutCommentItems = (RelativeLayout) itemView.findViewById(R.id.layoutCommentItems);
        mLayoutChatItems = (RelativeLayout) itemView.findViewById(R.id.layoutChatItems);

        mAutoTextViewChatDate = (AutoTypeTextView) itemView.findViewById(R.id.dateAutoTextChat);
        mAutoTextViewChatUser = (AutoTypeTextView) itemView.findViewById(R.id.chatUserAutoText);
        mCardViewChatDetail = (CardView) itemView.findViewById(R.id.chatDetailCardView);
        mFabCancelChat = (FloatingActionButton) itemView.findViewById(R.id.fabCancelChat);
        mFabDeleteChat = (FloatingActionButton) itemView.findViewById(R.id.fabDeleteChat);
        mTextViewDeleteChat = (TextView) itemView.findViewById(R.id.textViewDeleteChat);
        mCardViewDeleteChat = (CardView) itemView.findViewById(R.id.cardView_deleteChat);
        mLayoutDeleteChat = (RelativeLayout) itemView.findViewById(R.id.layout_deleteChat);
        mTextViewChatUser = (TextView) itemView.findViewById(R.id.chatTextViewUser);
        mCardViewChatUser = (CardView) itemView.findViewById(R.id.chatCardViewUser);
        mTextViewChat = (TextView) itemView.findViewById(R.id.chatTextView);
        mCardViewChat = (CardView)itemView.findViewById(R.id.chatCardView);
        mImageViewPost = (ImageView) itemView.findViewById(R.id.chatImageView);

    }


}
