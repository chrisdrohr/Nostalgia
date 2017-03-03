package com.example.rohrlabs.nostalgia.ViewHolders;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.rohrlabs.nostalgia.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewholderUser extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public CircleImageView mCircleImageViewGroupMember;
    public AutoTypeTextView mAutoTypeTextViewGroupMember;

    public ViewholderUser(View itemView) {
        super(itemView);

        mAutoTypeTextViewGroupMember = (AutoTypeTextView) itemView.findViewById(R.id.autoTypeTextViewGroupMember);
        mCircleImageViewGroupMember = (CircleImageView) itemView.findViewById(R.id.circleImageViewGroupMember);
    }


}
