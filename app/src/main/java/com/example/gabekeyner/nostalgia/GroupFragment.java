package com.example.gabekeyner.nostalgia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupFragment extends DialogFragment {

    private String mUsername, mPhotoUrl, mUid, groupName;
    private Context context;
    private TextView textView;
    private CircleImageView circleUserImageView;
    private EditText mEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_group, null);
        builder.setView(view);
        circleUserImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        context = getActivity();

        Glide.with(this)
                .load(mPhotoUrl)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleUserImageView);
        textView.setText(mUsername);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEditText = (EditText) view.findViewById(R.id.groupEditText);
                groupName = mEditText.getText().toString();
                User user = new User(
                        mUsername,
                        mPhotoUrl,
                        mUid,
                        null,
                        null);
                FirebaseUtil.getGroupRef().child(groupName).push().setValue(user);
                Intent intent = new Intent(context, GroupsActivity.class);
                intent.putExtra("groupName",mEditText.getText().toString());
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
