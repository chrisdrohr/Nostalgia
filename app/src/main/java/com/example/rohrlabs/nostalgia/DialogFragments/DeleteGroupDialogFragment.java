package com.example.rohrlabs.nostalgia.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.rohrlabs.nostalgia.Activities.MainActivity;
import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DeleteGroupDialogFragment extends DialogFragment implements View.OnClickListener{

    private FloatingActionButton mFabDelete, mFabCancel;
    private AutoTypeTextView mAutoTypeTextView;
    private ImageView mImageViewGroupDelete;
    private DatabaseReference mDeleteGroupRef, mGroupExistsRef;
    private String mGroupKey, mGroupImage, mGroupName;
    public static String mCheckGroupDeleteKey;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_group_delete_dialog, null);
        builder.setView(view);
        mGroupKey = GroupsAdapter.mGroupKey;
        mGroupImage = GroupsAdapter.groupPhoto;
        mGroupName = GroupsAdapter.groupName;
        mFabCancel = (FloatingActionButton) view.findViewById(R.id.fabGroupCancel);
        mFabDelete = (FloatingActionButton) view.findViewById(R.id.fabGroupDelete);
        mAutoTypeTextView = (AutoTypeTextView) view.findViewById(R.id.autoTextGroupDelete);
        mDeleteGroupRef = FirebaseUtil.getGroupKeyRef();
        mImageViewGroupDelete = (ImageView) view.findViewById(R.id.imageViewGroupDeleteBg);

        mFabCancel.setOnClickListener(this);
        mFabDelete.setOnClickListener(this);

        Glide.with(DeleteGroupDialogFragment.this)
                .load(mGroupImage)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.1f)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageViewGroupDelete);

        mAutoTypeTextView.setTextAutoTyping("Delete this group?");
        mAutoTypeTextView.setTypingSpeed(20);
        mAutoTypeTextView.setFontFeatureSettings("fonts/Roboto-Regular.ttf");

//        getGroupMembersList();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabGroupCancel:
                dismiss();
                break;
            case R.id.fabGroupDelete:
                groupDelete();
                break;
            default:
        }
    }
//    void getGroupMembersList() {
//        getChildFragmentManager()
//                .beginTransaction()
//                .add(R.id.containerGroupMemberList, FragmentUtils.getGroupMembersList())
//                .commit();
//    }

    void groupDelete () {
        mGroupExistsRef = FirebaseUtil.getGroupKeyRef().child("members");
        mGroupExistsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String mMemberUid = child.getKey();
                    Log.i("datasnapshot ", child.getKey());
                    FirebaseUtil.getBaseRef().child("members").child(mMemberUid).child("groups").child(mGroupKey).removeValue();
                    FirebaseUtil.getGroupKeyRef().removeValue();
                }
                startActivity(new Intent(getActivity(), MainActivity.class));
                mGroupKey = null;
                mCheckGroupDeleteKey = "delete";
                Toast.makeText(getActivity(), "Group '" + mGroupName + "' Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
