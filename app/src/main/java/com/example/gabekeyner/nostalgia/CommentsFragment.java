package com.example.gabekeyner.nostalgia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.dragankrstic.autotypetextview.AutoTypeTextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.gabekeyner.nostalgia.DetailActivity.COMMENTS_CHILD;

public class CommentsFragment extends DialogFragment{

    private CircleImageView userImageView;
    private String mPhotoUrl, mUsername;
    private TextView textView;
    private AutoTypeTextView autoTypeTextView;
    private EditText mEditText;
    private String mPost_key = null;
    private FloatingActionButton mSendFab;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_comments, null);

        userImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
//        autoTypeTextView = (AutoTypeTextView) view.findViewById(R.id.commentAutoText);
        mEditText = (EditText) view.findViewById(R.id.commentEditText);

        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();

        // Send function to comment
        SimpleDateFormat time = new SimpleDateFormat("dd/MM-hh:mm");
        final String mCurrentTimestamp = time.format(new Date());
        mSendFab = (FloatingActionButton) view.findViewById(R.id.sendFab);
        AnimationUtil.setFadeAnimation(mSendFab);
        mSendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new
                        Comment(mEditText.getText().toString(),
                        mUsername,
                        mPhotoUrl,
                        mCurrentTimestamp
                );
                FirebaseUtil.getBaseRef().child(COMMENTS_CHILD)
                        .push().setValue(comment);
                mEditText.setText("");
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mSendFab.setEnabled(true);
                } else {
                    mSendFab.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Glide.with(CommentsFragment.this)
                .load(mPhotoUrl)
                .priority(Priority.IMMEDIATE)
                .into(userImageView);
        textView.setText(mUsername);
//        autoTypeTextView.setTextAutoTyping("Comment on this photo");
//        autoTypeTextView.setTypingSpeed(20);

        builder.setView(view);
        builder.setPositiveButton("Post",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mSendFab.callOnClick();
                        ((DetailActivity)getActivity()).postComment();
                    }
                }
        )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
//                                (()getActivity()).doNegativeClick();
                            }
                        }
                );
        return builder.create();
    }
}
