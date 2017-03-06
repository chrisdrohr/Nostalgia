package com.example.rohrlabs.nostalgia.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.R;

public class GroupMembersFragment extends android.app.DialogFragment implements View.OnClickListener{

    private static final String TAG = "GroupMembersFragment";
    private FloatingActionButton mFabExit;
    private String mGroupKey, mGroupImage, mGroupName;
    private ImageView mImageViewGroupMembers;

    public GroupMembersFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.group_members_layout, null);
        builder.setView(view);

        mGroupKey = GroupsAdapter.mGroupKey;
        mGroupImage = GroupsAdapter.groupPhoto;
        mGroupName = GroupsAdapter.groupName;

        mImageViewGroupMembers = (ImageView) view.findViewById(R.id.imageViewGroupMembersBg);

        Glide.with(GroupMembersFragment.this)
                .load(mGroupImage)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.1f)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageViewGroupMembers);

        mFabExit = (FloatingActionButton) view.findViewById(R.id.fabExit);
        mFabExit.setOnClickListener(this);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return alertDialog;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fabExit:
                dismiss();
//                getGroupMembersList();
                break;

            default:
        }
    }

//        void getGroupMembersList() {
//        getFragmentManager()
//                .beginTransaction()
//                .add(R.id.groupMemberFragment, FragmentUtils.getGroupMembersList())
//                .commit();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
