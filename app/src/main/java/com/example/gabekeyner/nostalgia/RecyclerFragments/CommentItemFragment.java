package com.example.gabekeyner.nostalgia.RecyclerFragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gabekeyner.nostalgia.Adapters.CommentAdapter;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Comment;
import com.example.gabekeyner.nostalgia.R;
import com.example.gabekeyner.nostalgia.Viewholder;

public class CommentItemFragment extends Fragment {

    private static final String TAG = "CommentItemFragment";
    private RecyclerView mRecyclerView;
    private Context context;
    private String mPostKey;
    private CommentAdapter mCommentAdapter;
    private LinearLayoutManager mLayoutManager;

    public CommentItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPostKey = getActivity().getIntent().getStringExtra("post_key");
//        Toast.makeText(getContext(), mPostKey, Toast.LENGTH_SHORT).show();
        mCommentAdapter = new CommentAdapter(
                Comment.class,
                R.layout.fragment_comment_item,
                Viewholder.class,
                FirebaseUtil.getCommentsRef().child(mPostKey),
                getContext());
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setStackFromEnd(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment_item_list, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommentList);
        mRecyclerView.setLayoutManager(mLayoutManager);
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
