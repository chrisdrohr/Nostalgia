package com.example.rohrlabs.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rohrlabs.nostalgia.Adapters.AddedUserAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;

public class AddedUserItemFragment extends Fragment {
    private static final String TAG = "AddedUserItemFragment";
    private RecyclerView mRecyclerView;
    private AddedUserAdapter mAddedUserAdapter;
    private LinearLayoutManager mLayoutManager;
    private String groupKey, mUid;

    public AddedUserItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        groupKey = getActivity().getIntent().getStringExtra("mGroupKey");
        mUid = FirebaseUtil.getUid();

        mAddedUserAdapter = new AddedUserAdapter(
                User.class,
                R.layout.fragment_added_user_item,
                Viewholder.class,
                FirebaseUtil.getGroupRef().child(groupKey).child("members"),
                getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAddedUserAdapter != null) {
            mAddedUserAdapter.cleanup();
        }
    }
}
