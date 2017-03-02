package com.example.rohrlabs.nostalgia.ViewHolders;


import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.rohrlabs.nostalgia.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewholderChat extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public CardView mCardViewChat, mCardViewChatUser, mCardViewChatDelete, mCardViewChatDetail;
    public TextView mTextViewChat, mTextViewChatUser, mTextViewChatDelete;
    public AutoTypeTextView mAutoTextViewChatUser, mAutoTextViewChatDate;
    public FloatingActionButton mFabChatDelete, mFabChatCancel;
    public RelativeLayout mLayoutChatDelete, mLayoutChatItems;
    public CircleImageView mCircleImageViewChat;

    public ViewholderChat(View itemView) {
        super(itemView);

        mLayoutChatItems = (RelativeLayout) itemView.findViewById(R.id.layoutChatItems);
        mLayoutChatDelete = (RelativeLayout) itemView.findViewById(R.id.layoutChatDelete);

        mAutoTextViewChatDate = (AutoTypeTextView) itemView.findViewById(R.id.autoTextChatDate);
        mAutoTextViewChatUser = (AutoTypeTextView) itemView.findViewById(R.id.autoTextChatUser);
        mCardViewChatDetail = (CardView) itemView.findViewById(R.id.cardViewChatDetail);
        mFabChatCancel = (FloatingActionButton) itemView.findViewById(R.id.fabChatCancel);
        mFabChatDelete = (FloatingActionButton) itemView.findViewById(R.id.fabChatDelete);
        mTextViewChatDelete = (TextView) itemView.findViewById(R.id.textViewChatDelete);
        mCardViewChatDelete = (CardView) itemView.findViewById(R.id.cardViewChatDelete);
        mTextViewChatUser = (TextView) itemView.findViewById(R.id.textViewChatUser);
        mCardViewChatUser = (CardView) itemView.findViewById(R.id.cardViewChatUser);
        mTextViewChat = (TextView) itemView.findViewById(R.id.textViewChat);
        mCardViewChat = (CardView)itemView.findViewById(R.id.cardViewChat);
        mCircleImageViewChat = (CircleImageView) itemView.findViewById(R.id.circleImageViewChat);

    }


}
