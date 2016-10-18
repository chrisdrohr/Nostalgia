package com.example.gabekeyner.nostalgia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gabekeyner.nostalgia.DatabaseActivitys.Constants;
import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.gabekeyner.nostalgia.R.id.recyclerView;

public class PostListActivity extends AppCompatActivity {
    private DatabaseReference mPostReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @BindView(recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPostReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_POSTS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Post, FirebaseViewHolder>(Post.class, R.layout.card_view, FirebaseViewHolder.class, mPostReference) {

            @Override
            protected void populateViewHolder(FirebaseViewHolder viewHolder, Post model, int position) {
                viewHolder.bindPost(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
