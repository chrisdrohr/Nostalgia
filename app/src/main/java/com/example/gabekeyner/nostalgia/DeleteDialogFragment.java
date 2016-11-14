package com.example.gabekeyner.nostalgia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.dragankrstic.autotypetextview.AutoTypeTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeleteDialogFragment extends DialogFragment {

    private ImageButton deleteButton, cancelButton;
    private CircleImageView userImageView;
    private String mPhotoUrl, mUsername;
    private TextView textView;
    private AutoTypeTextView autoTypeTextView;
    private String mPost_key = null;


//    public static MyAlertDialogFragment newInstance(int title) {
//        MyAlertDialogFragment frag = new MyAlertDialogFragment();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        frag.setArguments(args);
//        return frag;
//    }




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_delete_dialog, null);

        userImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
        autoTypeTextView = (AutoTypeTextView) view.findViewById(R.id.deleteAutoText);

        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();

        Glide.with(DeleteDialogFragment.this)
                .load(mPhotoUrl)
                .priority(Priority.IMMEDIATE)
                .into(userImageView);
        textView.setText(mUsername);
        autoTypeTextView.setTextAutoTyping("Would you like to remove this post?");
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
