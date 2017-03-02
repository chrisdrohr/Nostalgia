package com.example.rohrlabs.nostalgia.ViewHolders;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rohrlabs.nostalgia.R;

public class ViewholderPost extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public TextView mTextViewPostTitle, mTextViewPostUserName;
    public ImageView mImageViewPost;
    public CardView mCardViewPost;
    public Context mContext;

    public ViewholderPost(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mTextViewPostTitle = (TextView) itemView.findViewById(R.id.textViewPostTitle);
        mTextViewPostUserName = (TextView) itemView.findViewById(R.id.textViewPostUserName);
        mCardViewPost = (CardView) itemView.findViewById(R.id.cardViewPost);
        mImageViewPost = (ImageView) itemView.findViewById(R.id.imageViewPost);
    }
}
