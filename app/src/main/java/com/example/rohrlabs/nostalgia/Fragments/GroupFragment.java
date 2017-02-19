package com.example.rohrlabs.nostalgia.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rohrlabs.nostalgia.R;

public class GroupFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private FloatingActionButton mFabAddGroup;


    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_group_tab, container, false);
        mFabAddGroup = (FloatingActionButton) view.findViewById(R.id.fabAddGroup);

        mFabAddGroup.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fabAddGroup:
                openGroupsActivity();
                Toast.makeText(getActivity(), "fj", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    public void openGroupsActivity () {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment groupFragment = new com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment();
        groupFragment.show(fragmentManager, "Group Fragment");
    }

}
