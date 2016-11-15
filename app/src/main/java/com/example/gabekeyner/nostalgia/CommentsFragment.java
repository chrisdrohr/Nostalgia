package com.example.gabekeyner.nostalgia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.dragankrstic.autotypetextview.AutoTypeTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsFragment extends DialogFragment{

    private CircleImageView userImageView;
    private String mPhotoUrl, mUsername;
    private TextView textView;
    private AutoTypeTextView autoTypeTextView;
    private String mPost_key = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_comments, null);

        userImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
        autoTypeTextView = (AutoTypeTextView) view.findViewById(R.id.commentAutoText);

        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();

        Glide.with(CommentsFragment.this)
                .load(mPhotoUrl)
                .priority(Priority.IMMEDIATE)
                .into(userImageView);
        textView.setText(mUsername);
        autoTypeTextView.setTextAutoTyping("Comment on this photo");
        autoTypeTextView.setTypingSpeed(20);

        builder.setView(view);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ((DetailActivity)getActivity()).doPositiveClick();
                    }
                }
        )
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                (()getActivity()).doNegativeClick();
                            }
                        }
                );
        return builder.create();
    }
}
