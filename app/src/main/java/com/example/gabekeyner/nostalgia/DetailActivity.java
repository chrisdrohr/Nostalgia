package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView commentTextView;
        public TextView commentNameTextView;
        public CircleImageView commentImageView;
        public AutoTypeTextView commentAutoTypeTextView;


        public MessageViewHolder(View v) {
            super(v);
            commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
            commentNameTextView = (TextView) itemView.findViewById(R.id.commentNameTextView);
            commentImageView = (CircleImageView) itemView.findViewById(R.id.commentImageView);
            commentAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.dateAutoText);
        }
    }
    public static final String TITLE = "title";
    public static final String COMMENTS_CHILD = "comments";
    private ProgressBar mProgressBar;
    private RecyclerView mCommentRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mSendFab;

    private String mUsername;
    private String mPhotoUrl;
    private String mTimestamp;
    private EditText mEditText;

    // Firebase instance variables
    private FirebaseRecyclerAdapter<Comment, MessageViewHolder>mFirebaseAdapter;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseLike;

    private Animation fade_in;
    private TextView titleTxt, imageViewText;
    private ImageView imageView, imageViewer;
    private String title;

    private Boolean mProcessLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize ProgressBar and RecyclerView.
        mCommentRecyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mCommentRecyclerView.setLayoutManager(mLinearLayoutManager);

        //Initialize Firebase Auth
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mDatabaseLike = FirebaseUtil.getLikesRef();
        SimpleDateFormat time = new SimpleDateFormat("dd/MM/yyyy-hh:mm");
        final String mTimestamp = time.format(new Date());


        mDatabaseLike.keepSynced(true);




        // New child entries
//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Comment, MessageViewHolder>(
                Comment.class,
                R.layout.item_comment,
                MessageViewHolder.class,
                FirebaseUtil.getBaseRef().child(COMMENTS_CHILD)) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Comment model, int position) {
                viewHolder.commentTextView.setText(model.getText());
                viewHolder.commentNameTextView.setText(mUsername);
                viewHolder.commentAutoTypeTextView.setTextAutoTyping(mTimestamp);
                viewHolder.commentAutoTypeTextView.setTypingSpeed(100);

                if (model.getPhotoUrl() == null) {
                    viewHolder.commentImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(DetailActivity.this,
                                            R.drawable.ic_account_circle_black_36dp));
                } else {
                    Glide.with(DetailActivity.this)
                            .load(mPhotoUrl)
                            .priority(Priority.NORMAL)
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

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_detail);

        titleTxt = (TextView) findViewById(R.id.detailTitle);
        imageView = (ImageView) findViewById(R.id.detialView);
        titleTxt.startAnimation(fade_in);



        //Receive Data
        Intent intent = this.getIntent();
        String title = intent.getExtras().getString("title");
        final String imageUrl = intent.getExtras().getString("imageURL");

        //Bind Data
        titleTxt.setText(title);
        Glide.with(this).load(imageUrl).thumbnail(0.1f).centerCrop().priority(Priority.IMMEDIATE).into(imageView);
        ImageButton mLikeButton;
        mLikeButton = (ImageButton) findViewById(R.id.likeButton);
//        mLikeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mProcessLike = true;
//
//                if (mProcessLike) {
//                    mDatabaseLike.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.child(post_key).hasChild(FirebaseUtil.getUser().getUid)){
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    })
//                }
//            }
//        });

        // Send function to comment

        mEditText = (EditText) findViewById(R.id.commentEditText);
        mSendFab = (FloatingActionButton) findViewById(R.id.sendFab);
        mSendFab.startAnimation(fade_in);
        mSendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new
                        Comment(mEditText.getText().toString(),
                        mUsername,
                        mPhotoUrl,
                        mTimestamp
                        );
                FirebaseUtil.getBaseRef().child(COMMENTS_CHILD)
                        .push().setValue(comment);
                mEditText.setText("");
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mSendFab.setEnabled(true);
                } else {
                    mSendFab.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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