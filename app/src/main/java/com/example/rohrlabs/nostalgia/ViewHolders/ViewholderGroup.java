package com.example.rohrlabs.nostalgia.ViewHolders;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohrlabs.nostalgia.R;

public class ViewholderGroup extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public TextView mTextViewGroupName;
    public ImageView mImageViewGroup;
    public CardView mCardViewGroup;

    public ViewholderGroup(View itemView) {
        super(itemView);

        mImageViewGroup = (ImageView) itemView.findViewById(R.id.imageViewGroup);
        mTextViewGroupName = (TextView) itemView.findViewById(R.id.textViewGroup);
        mCardViewGroup = (CardView) itemView.findViewById(R.id.cardViewGroup);
    }
}
