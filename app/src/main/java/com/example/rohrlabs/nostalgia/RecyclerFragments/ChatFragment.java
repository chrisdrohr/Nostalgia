package com.example.rohrlabs.nostalgia.RecyclerFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rohrlabs.nostalgia.Adapters.CommentAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.ObjectClasses.Comment;
import com.example.rohrlabs.nostalgia.R;
import com.example.rohrlabs.nostalgia.Viewholder;

public class ChatFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "CommentItemFragment";
    private RecyclerView mRecyclerView;
    private Context context;
    private String mKey;
    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mLayoutManager;

    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        mPostKey = getActivity().getIntent().getStringExtra("post_key");
//        Toast.makeText(getContext(), mPostKey, Toast.LENGTH_SHORT).show();
        mCommentAdapter = new CommentAdapter(
                Comment.class,
                R.layout.fragment_comment_item,
                Viewholder.class,
                FirebaseUtil.getCommentsRef(),
                getContext());
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mLayoutManager.setStackFromEnd(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommentList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mCommentAdapter);
        mCommentAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mCommentAdapter.getItemCount();
                int lastVisiblePosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
        return rootView;
    }


}
