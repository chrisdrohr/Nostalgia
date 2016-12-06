package com.example.gabekeyner.nostalgia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupFragment extends DialogFragment implements EditText.OnEditorActionListener{

    public interface GroupFragmentListener {
        void onFinishGroupFragment(String groupName);
    }

    private EditText mEditText;
    private Context context;
    private TextView textView;
    private CircleImageView userImageView;
    private DatabaseReference groupNamePath;
    private String mPhotoUrl, mUsername, mUid, groupName;
    private SimpleDateFormat time = new SimpleDateFormat("MM/DD");
    private GroupsActivity groupsActivity;
    private String timeStamp = time.format(new Date());

    public GroupFragment() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_group, null);

        mEditText = (EditText) view.findViewById(R.id.groupEditText);
        userImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
        groupNamePath = FirebaseUtil.getGroupRef();
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        groupName = mEditText.getText().toString();
        context = getActivity();

        mEditText.setOnEditorActionListener(this);

//        mEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().trim().length() > 0) {
//                    .setEnabled(true);
//                } else {
//                    mSendFab.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        Glide.with(GroupFragment.this)
                .load(mPhotoUrl)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImageView);
        textView.setText(mUsername);

        builder.setView(view);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
        return builder.create();

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            GroupFragmentListener activity = (GroupFragmentListener) getActivity();
            activity.onFinishGroupFragment(groupName);
            this.dismiss();
            return true;
        }
        return false;
    }
}
