package com.example.gabekeyner.nostalgia;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public static final String TITLE = "title";
    public static final String IMAGE = "image";
    public static final int REQUEST = 0;

    public TextView mTitle;
    public ImageView mImageView;
    public CardView mCardView;
    public Context mContext;

    //Gabe Add
    public ImageView detailImageView;
    public TextView detailTitle;

    public Viewholder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mTitle = (TextView) itemView.findViewById(R.id.textView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        mCardView = (CardView) itemView.findViewById(R.id.cardView);
        mContext = itemView.getContext();

        detailImageView = (ImageView) itemView.findViewById(R.id.detialView);
        detailTitle = (TextView) itemView.findViewById(R.id.detailTitle);
    }

    @Override
    public void onClick(View v) {

    }
}
