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
public class ViewHolderComment extends RecyclerView.ViewHolder{

    private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();

    public CardView mCardViewComment, mCardViewCommentDelete, mCardViewCommentDetails, mCardViewCommentUser;
    public TextView mTextViewComment, mTextViewCommentUser;
    public AutoTypeTextView mAutoTextViewComment, mAutoTextViewCommentTimestamp;
    public RelativeLayout mLayoutCommentItems, mLayoutCommentDelete;
    public CircleImageView mCircleImageViewComment;
    public FloatingActionButton mFabCancel, mFabDelete;

    public ViewHolderComment(View itemView) {
        super(itemView);
        mCircleImageViewComment = (CircleImageView) itemView.findViewById(R.id.circleImageViewComment);
        mTextViewComment = (TextView) itemView.findViewById(R.id.textViewComment);
        mTextViewCommentUser = (TextView) itemView.findViewById(R.id.textViewCommentUser);
        mAutoTextViewComment = (AutoTypeTextView) itemView.findViewById(R.id.autoTextComment);
        mAutoTextViewCommentTimestamp = (AutoTypeTextView) itemView.findViewById(R.id.autoTextCommentTimestamp);
        mCardViewCommentDelete = (CardView) itemView.findViewById(R.id.cardViewCommentDelete);
        mCardViewCommentDetails = (CardView) itemView.findViewById(R.id.cardViewCommentDetails);
        mCardViewComment = (CardView) itemView.findViewById(R.id.cardViewComment);
        mCardViewCommentUser = (CardView) itemView.findViewById(R.id.cardViewCommentUser);
        mLayoutCommentDelete = (RelativeLayout) itemView.findViewById(R.id.layoutCommentDelete);
        mLayoutCommentItems = (RelativeLayout) itemView.findViewById(R.id.layoutCommentItems);
        mFabCancel = (FloatingActionButton) itemView.findViewById(R.id.fabCommentCancel);
        mFabDelete = (FloatingActionButton) itemView.findViewById(R.id.fabCommentDelete);
    }
}
