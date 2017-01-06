package com.example.gabekeyner.nostalgia.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.gabekeyner.nostalgia.Activities.DetailActivity;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.R;
import com.github.florent37.viewanimator.ViewAnimator;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeleteDialogFragment extends DialogFragment {

    private ImageButton deleteButton, cancelButton;
    private CircleImageView userImageView;
    private String mPhotoUrl, mUsername;
    private CardView cardView;
    private TextView textView;
    private AutoTypeTextView autoTypeTextView;
    private ImageView deleteBg;
    private String mPost_key = null;
    public static String post_image;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_delete_dialog, null);

        userImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
        autoTypeTextView = (AutoTypeTextView) view.findViewById(R.id.deleteAutoText);
        cardView = (CardView) view.findViewById(R.id.cardViewprofile);

        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        deleteBg = (ImageView) view.findViewById(R.id.delete_bg);
        post_image = DetailActivity.post_image;

        Glide.with(DeleteDialogFragment.this)
                .load(post_image)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.1f)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(deleteBg);

        Glide.with(DeleteDialogFragment.this)
                .load(mPhotoUrl)
                .priority(Priority.IMMEDIATE)
                .into(userImageView);
        textView.setText(mUsername);
        autoTypeTextView.setTextAutoTyping("Would you like to remove this post?");
        autoTypeTextView.setTypingSpeed(20);
        autoTypeTextView.setFontFeatureSettings("fonts/Roboto-Regular.ttf");

        ViewAnimator.animate(cardView)
                .zoomIn()
                .duration(200)
                .andAnimate(userImageView, textView)
                .alpha(0,0)
                .thenAnimate(userImageView)
                .alpha(0,1)
                .rollIn()
                .duration(200)
                .thenAnimate(textView)
                .slideBottom()
                .duration(150)
                .thenAnimate(autoTypeTextView)
                .pulse()
                .duration(500)
                .start();

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
