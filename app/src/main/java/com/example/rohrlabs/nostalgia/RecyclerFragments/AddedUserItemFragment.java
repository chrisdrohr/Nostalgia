package com.example.rohrlabs.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rohrlabs.nostalgia.Adapters.AddedUserAdapter;
import com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;

public class AddedUserItemFragment extends Fragment {
    private static final String TAG = "AddedUserItemFragment";
    private RecyclerView mRecyclerView;
    private AddedUserAdapter mAddedUserAdapter;
    private LinearLayoutManager mLayoutManager;
    private String groupKey, groupName;

    public AddedUserItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        groupKey = getActivity().getIntent().getStringExtra("mGroupKey");

        mAddedUserAdapter = new AddedUserAdapter(User.class, R.layout.fragment_added_user_item, Viewholder.class, FirebaseUtil.getGroupRef().child(GroupFragment.mGroupKey).child("members"), getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_added_user_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewAddedUserlist);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAddedUserAdapter);
        return rootView;
    }

}
