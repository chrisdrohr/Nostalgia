package com.example.rohrlabs.nostalgia.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.rohrlabs.nostalgia.Activities.DetailActivity;
import com.example.rohrlabs.nostalgia.Activities.MainActivity;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.R;
import com.google.firebase.database.DatabaseReference;

import static com.example.rohrlabs.nostalgia.Activities.DetailActivity.post_image;

public class DeleteDialogFragment extends DialogFragment implements View.OnClickListener{

    private FloatingActionButton mFabDelete, mFabCancel;
    private AutoTypeTextView mAutoTypeTextView;
    private ImageView mImageViewPostDelete;
    private DatabaseReference mDeletePostRef;
    private String mPostKey;
    private String mPostImage;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_delete_dialog, null);
        builder.setView(view);
        mPostImage = post_image;
        mPostKey = DetailActivity.mPostKey;
        mFabCancel = (FloatingActionButton) view.findViewById(R.id.fabPostCancel);
        mFabDelete = (FloatingActionButton) view.findViewById(R.id.fabPostDelete);
        mAutoTypeTextView = (AutoTypeTextView) view.findViewById(R.id.autoTextPostDelete);
        mDeletePostRef = FirebaseUtil.getDeletePostRef().child(mPostKey);
        mImageViewPostDelete = (ImageView) view.findViewById(R.id.delete_bg);

        mFabCancel.setOnClickListener(this);
        mFabDelete.setOnClickListener(this);

        Glide.with(DeleteDialogFragment.this)
                .load(mPostImage)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.1f)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageViewPostDelete);

        mAutoTypeTextView.setTextAutoTyping("Would you like to remove this post?");
        mAutoTypeTextView.setTypingSpeed(20);
        mAutoTypeTextView.setFontFeatureSettings("fonts/Roboto-Regular.ttf");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabPostCancel:
                dismiss();
                break;
            case R.id.fabPostDelete:
                Toast.makeText(getActivity(), "Post Deleted", Toast.LENGTH_SHORT).show();
                mDeletePostRef.removeValue();
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            default:
        }
    }
}
