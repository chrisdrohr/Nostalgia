package com.example.gabekeyner.nostalgia;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Viewholder extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public TextView mTitle;
    public ImageView mImageView;
    public TextView mUsername;
    public CardView mCardView;
    public Context mContext;
    public ImageView detailImageView;
    public TextView detailTitle;

    public Viewholder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.textView);
        mUsername = (TextView) itemView.findViewById(R.id.usernameTextView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        mCardView = (CardView) itemView.findViewById(R.id.cardView);
        mContext = itemView.getContext();
        detailImageView = (ImageView) itemView.findViewById(R.id.detialView);
        detailTitle = (TextView) itemView.findViewById(R.id.detailTitle);
    }


}
