package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gabekeyner.nostalgia.DatabaseActivitys.Comment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView commentTextView;
        public TextView commentNameTextView;
        public CircleImageView commentImageView;

        public MessageViewHolder(View v) {
            super(v);
            commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
            commentNameTextView = (TextView) itemView.findViewById(R.id.commentNameTextView);
            commentImageView = (CircleImageView) itemView.findViewById(R.id.commentImageView);
        }
    }

    public static final String MESSAGES_CHILD = "comments";
    private ProgressBar mProgressBar;
    private RecyclerView mCommentRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mSendFab;

    private String mUsername;
    private String mPhotoUrl;
    private EditText mEditText;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Comment, MessageViewHolder>mFirebaseAdapter;


    private ArrayList<String> itemList;

    Animation fade_in;
    TextView titleTxt;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize ProgressBar and RecyclerView.
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mCommentRecyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mCommentRecyclerView.setLayoutManager(mLinearLayoutManager);

// New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Comment, MessageViewHolder>(
                Comment.class,
                R.layout.item_comment,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Comment model, int position) {
//                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.commentTextView.setText(model.getText());
                viewHolder.commentNameTextView.setText(model.getName());
                if (model.getPhotoUrl() == null) {
                    viewHolder.commentImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(DetailActivity.this,
                                            R.drawable.ic_account_circle_black_36dp));
                } else {
                    Glide.with(DetailActivity.this)
                            .load(model.getPhotoUrl())
                            .into(viewHolder.commentImageView);
                }
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mCommentRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mCommentRecyclerView.setLayoutManager(mLinearLayoutManager);
        mCommentRecyclerView.setAdapter(mFirebaseAdapter);

//        //TODO adding comment section (using arraylist)
//        String[] items = {"Apple", "Banana", "Clementine"};
//        itemList = new ArrayList<String>(Arrays.asList(items));
//        adapter = new ArrayAdapter<>(
//                this,
//                R.layout.comment_item,
//                R.id.comment_txt_item, itemList);
//
//        ListView LV = (ListView) findViewById(R.id.postedComments);
//        LV.setAdapter(adapter);
//        typeComment = (EditText) findViewById(R.id.comment);


        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_detail);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendFab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Comment Posted", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
//                String newItem = typeComment.getText().toString();
//                // Add new comment to ArrayList TODO need to add comment to database
//                itemList.add(newItem);
////                adapter.notifyDataSetChanged();
//
//            }
//        });

        titleTxt = (TextView) findViewById(R.id.detailTitle);
        imageView = (ImageView) findViewById(R.id.detialView);
//        sendFab.startAnimation(fade_in);
        titleTxt.startAnimation(fade_in);


        //Receive Data
        Intent intent = this.getIntent();
        String title = intent.getExtras().getString("title");
        String imageUrl = intent.getExtras().getString("imageUrl");

        //Bind Data

        titleTxt.setText(title);
        PicassoClient.downloadImage(this, imageUrl, imageView);

        // Send function to comment
        mEditText = (EditText) findViewById(R.id.commentEditText);
        mSendFab = (FloatingActionButton) findViewById(R.id.sendFab);
        mSendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new
                        Comment(mEditText.getText().toString(),
                        mUsername,
                        mPhotoUrl);
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                        .push().setValue(comment);
                mEditText.setText("");
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}