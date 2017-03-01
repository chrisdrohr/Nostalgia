package com.example.rohrlabs.nostalgia.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rohrlabs.nostalgia.FragmentUtils;
import com.example.rohrlabs.nostalgia.R;

public class GroupMembersFragment extends android.app.Fragment implements View.OnClickListener{

    private static final String TAG = "GroupMembersFragment";
    private FloatingActionButton mFabExit;

    public GroupMembersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.group_members_layout, container, false);

        mFabExit = (FloatingActionButton) view.findViewById(R.id.fabExit);

        getMembersFragment();
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fabExit:

                break;

            default:
        }
    }

    void getMembersFragment () {
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.groupMemberFragment, FragmentUtils.getGroupMembersList())
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
