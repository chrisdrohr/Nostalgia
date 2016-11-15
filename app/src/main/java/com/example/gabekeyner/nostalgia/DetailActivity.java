package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView commentTextView;
        public TextView commentNameTextView;
        public CircleImageView commentImageView;
        public AutoTypeTextView commentAutoTypeTextView, commentTimestampAutoTextView;


        public MessageViewHolder(View v) {
            super(v);
            commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
            commentAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.userAutoText);
            commentImageView = (CircleImageView) itemView.findViewById(R.id.commentImageView);
            commentTimestampAutoTextView = (AutoTypeTextView) itemView.findViewById(R.id.dateAutoText);
        }
    }
    public static final String TITLE = "title";
    public static final String COMMENTS_CHILD = "comments";
    private ProgressBar mProgressBar;
    private RecyclerView mCommentRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mSendFab, mCommentFab;

    private String mUsername;
    private String mPhotoUrl;
    private String mTimestamp;
    private String mUid;
    private EditText mEditText;

    // Firebase instance variables
    private FirebaseRecyclerAdapter<Comment, MessageViewHolder>mFirebaseAdapter;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseLike, mDatabaseIntent;

    private Animation fade_in;
    private TextView titleTxt, imageViewText;
    private ImageView imageView, commentImageView, deleteImageView;
    private String title;
    private String mPost_key = null;
    private CardView mDeleteCardView, imageCardView, commentImageCardView;
    private ImageButton mDeleteButton, mLikeButton;
    private Boolean mProcessLike = false;
    private GestureDetector.OnDoubleTapListener mGestureDetector;
    private FragmentManager fragmentManager = getSupportFragmentManager();

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
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mDatabaseLike = FirebaseUtil.getLikesRef();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mGestureDetector = new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mLikeButton.callOnClick();
                Toast.makeText(DetailActivity.this, "double tap", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {

                return false;
            }
        };

        SimpleDateFormat time = new SimpleDateFormat("dd/MM-hh:mm");
        final String mCurrentTimestamp = time.format(new Date());

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
                viewHolder.commentAutoTypeTextView.setTextAutoTyping(mUsername);
                viewHolder.commentTimestampAutoTextView.setTextAutoTyping(mCurrentTimestamp);
                viewHolder.commentTimestampAutoTextView.setDecryptionSpeed(150);
                viewHolder.commentAutoTypeTextView.setTypingSpeed(50);

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

        titleTxt = (TextView) findViewById(R.id.commentDetailTitle);
        imageView = (ImageView) findViewById(R.id.detialView);
        imageCardView = (CardView) findViewById(R.id.cardViewDetail);
        mCommentRecyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        commentImageCardView = (CardView) findViewById(R.id.commentCardViewDetail);
        commentImageView = (ImageView) findViewById(R.id.commentDetialView);
        mCommentFab = (FloatingActionButton) findViewById(R.id.fabComment);
//        deleteImageView = (ImageView) findViewById(R.id.detialViewDelete);
//        mDeleteCardView = (CardView) findViewById(R.id.cardViewDelete);
//        mDeleteButton = (ImageButton) findViewById(R.id.deleteButton);
        titleTxt.startAnimation(fade_in);



        //Receive Data
        mDatabaseIntent = FirebaseUtil.getBaseRef().child("posts");
        mPost_key = getIntent().getExtras().getString("uid");
        mDatabaseIntent.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                final String post_image = (String) dataSnapshot.child("imageURL").getValue();
                String post_uid = (String) dataSnapshot.child("uid").getValue();

//                if (mUid.equals(post_uid)) {
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            Toast.makeText(DetailActivity.this, "yo", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            imageCardView.setVisibility(View.INVISIBLE);
                            commentImageCardView.setVisibility(View.VISIBLE);
//                            mCommentFab.startAnimation(fade_in);
                        mCommentFab.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCommentFab.setVisibility(View.VISIBLE);
                                AnimationUtil.setScaleAnimation(mCommentFab);
                            }
                        },1000);
                    }
                });
                commentImageCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageCardView.setVisibility(View.VISIBLE);
                        commentImageCardView.setVisibility(View.INVISIBLE);
                        mCommentFab.setVisibility(View.INVISIBLE);
                    }
                });

                //Bind Data
                titleTxt.setText(post_title);
                Glide.with(DetailActivity.this).load(post_image).thumbnail(0.1f).priority(Priority.IMMEDIATE).into(imageView);
                Glide.with(DetailActivity.this).load(post_image).thumbnail(0.1f).centerCrop().priority(Priority.LOW).into(commentImageView);
//                Toast.makeText(DetailActivity.this, post_uid, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Open Dialog Fragment
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog();
                return false;
            }
        });

//        mDeleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseUtil.getBaseRef().child(mPost_key).removeValue();
//                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

//        mLikeButton = (ImageButton) findViewById(R.id.likeButton);
//        mLikeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mProcessLike = true;
//                    mDatabaseLike.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            if (mProcessLike) {
//
//                                if (dataSnapshot.child(mPost_key).hasChild(mUid)) {
//                                    mDatabaseLike.child(mPost_key).child(mUid).removeValue();
//                                    mProcessLike = false;
//
//                                } else {
//                                    mDatabaseLike.child(mPost_key).child(mUid).setValue("like");
//                                    mProcessLike = false;
//                                }
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//        });
        // Send function to comment

//        mEditText = (EditText) findViewById(R.id.commentEditText);
//        mSendFab = (FloatingActionButton) findViewById(R.id.sendFab);
//        mSendFab.startAnimation(fade_in);
//        mSendFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Comment comment = new
//                        Comment(mEditText.getText().toString(),
//                        mUsername,
//                        mPhotoUrl,
//                        mCurrentTimestamp
//                        );
//                FirebaseUtil.getBaseRef().child(COMMENTS_CHILD)
//                        .push().setValue(comment);
//                mEditText.setText("");
//            }
//        });
//
//        mEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().trim().length() > 0) {
//                    mSendFab.setEnabled(true);
//                } else {
//                    mSendFab.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }
    public void setLikeBtn(String mPost_key){

    }

    void showDeleteDialog() {
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        deleteDialogFragment.show(fragmentManager, "Delete Dialog Fragment");
    }

    public void doPositiveClick() {
        FirebaseUtil.getBaseRef().child(mPost_key).removeValue();
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
        Toast.makeText(this, "Post Removed", Toast.LENGTH_SHORT).show();
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