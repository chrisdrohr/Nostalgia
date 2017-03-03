package com.example.rohrlabs.nostalgia.ViewHolders;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.rohrlabs.nostalgia.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewholderAddedUser extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public CircleImageView mCircleImageViewAddedUser;
    public AutoTypeTextView mAutoTextViewAddedUser;

    public ViewholderAddedUser(View itemView) {
        super(itemView);
        mAutoTextViewAddedUser = (AutoTypeTextView) itemView.findViewById(R.id.autoTextViewAddedUser);
        mCircleImageViewAddedUser = (CircleImageView) itemView.findViewById(R.id.circleImageViewAddedUser);
    }
}
