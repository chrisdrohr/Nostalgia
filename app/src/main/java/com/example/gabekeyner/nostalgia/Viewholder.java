package com.example.gabekeyner.nostalgia;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Viewholder extends RecyclerView.ViewHolder{

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public TextView mTitle;
    public ImageView mImageView;

    public Viewholder(View itemView) {
        super(itemView);
        mTitle = (TextView)itemView.findViewById(R.id.textView);
        mImageView = (ImageView)itemView.findViewById(R.id.imageView);
    }


}
