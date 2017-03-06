package com.example.rohrlabs.nostalgia.ViewHolders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohrlabs.nostalgia.R;

public class ViewholderUser extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public ImageView mImageViewUser;
    public TextView mTextViewUser;

    public ViewholderUser(View itemView) {
        super(itemView);

        mTextViewUser = (TextView) itemView.findViewById(R.id.textViewUser);
        mImageViewUser = (ImageView) itemView.findViewById(R.id.imageViewUser);
    }


}
