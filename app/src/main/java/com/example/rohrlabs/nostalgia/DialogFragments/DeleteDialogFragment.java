package com.example.rohrlabs.nostalgia.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
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
import com.example.rohrlabs.nostalgia.Activities.DetailActivity;
import com.example.rohrlabs.nostalgia.R;

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
        builder.setView(view);

        autoTypeTextView = (AutoTypeTextView) view.findViewById(R.id.deleteAutoText);

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

        autoTypeTextView.setTextAutoTyping("Would you like to remove this post?");
        autoTypeTextView.setTypingSpeed(20);
        autoTypeTextView.setFontFeatureSettings("fonts/Roboto-Regular.ttf");


//                builder.setView(view);
//                builder.setPositiveButton("Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                ((DetailActivity)getActivity()).doPositiveClick();
//                            }
//                        }
//                )
//                .setNegativeButton("No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
////                                (()getActivity()).doNegativeClick();
//                            }
//                        }
//                );
//                return builder.create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
