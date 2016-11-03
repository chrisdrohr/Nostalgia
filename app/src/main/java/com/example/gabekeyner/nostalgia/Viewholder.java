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
    public CardView mCardView;
    private Context mContext;

    public Viewholder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.textView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        mCardView = (CardView) itemView.findViewById(R.id.cardView);
        mContext = itemView.getContext();
    }

//    @Override
//    public void onClick(View v) {
//        int itemPosition = getLayoutPosition();
//        Intent intent = new Intent(mContext, DetailActivity.class);
//        Bundle bundle = new Bundle();
//        Toast.makeText(mContext, "click position" + itemPosition, Toast.LENGTH_SHORT).show();
//    }

}
