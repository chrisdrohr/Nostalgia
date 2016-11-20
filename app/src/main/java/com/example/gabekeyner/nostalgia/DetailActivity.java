package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import static com.example.gabekeyner.nostalgia.R.anim;
import static com.example.gabekeyner.nostalgia.R.color;
import static com.example.gabekeyner.nostalgia.R.drawable;
import static com.example.gabekeyner.nostalgia.R.id;
import static com.example.gabekeyner.nostalgia.R.layout;

public class DetailActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView commentTextView;
        public TextView commentNameTextView;
        public CircleImageView commentImageView;
        public AutoTypeTextView commentAutoTypeTextView, commentTimestampAutoTextView;

        public MessageViewHolder(View v) {
            super(v);
            commentTextView = (TextView) itemView.findViewById(id.commentTextView);
            commentAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(id.userAutoText);
            commentImageView = (CircleImageView) itemView.findViewById(id.commentImageView);
            commentTimestampAutoTextView = (AutoTypeTextView) itemView.findViewById(id.dateAutoText);
        }
    }
    public static final String TITLE = "title";
    public static final String COMMENTS_CHILD = "comments";
    private ProgressBar mProgressBar;
    private RecyclerView mCommentRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mSendFab, mCommentFab;
    private RelativeLayout relativeLayout;
    private ConstraintLayout constraintLayout;
    private Snackbar snackbar;

    private String mUsername;
    private String mPhotoUrl;
    private String mTimestamp;
    private String mUid;
    private EditText mEditText;

    // Firebase instance variables
    private FirebaseRecyclerAdapter<Comment, MessageViewHolder>mFirebaseAdapter;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseLike, mDatabaseIntent, mDatabase;

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
        setContentView(layout.detail_view);

        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize ProgressBar and RecyclerView.
        mCommentRecyclerView = (RecyclerView) findViewById(id.commentRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mCommentRecyclerView.setLayoutManager(mLinearLayoutManager);

        //Initialize Firebase Auth
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mDatabaseLike = FirebaseUtil.getLikesRef();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        mGestureDetector = new GestureDetector.OnDoubleTapListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                mLikeButton.callOnClick();
//                Toast.makeText(DetailActivity.this, "double tap", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//
//                return false;
//            }
//        };

        SimpleDateFormat time = new SimpleDateFormat("dd/MM-hh:mm");
        final String mCurrentTimestamp = time.format(new Date());

        mDatabaseLike.keepSynced(true);


        mFirebaseAdapter = new FirebaseRecyclerAdapter<Comment, MessageViewHolder>(
                Comment.class,
                layout.item_comment,
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
                                            drawable.ic_account_circle_black_36dp));
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
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
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

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), anim.fade_in_detail);

        titleTxt = (TextView) findViewById(id.commentDetailTitle);
        imageView = (ImageView) findViewById(id.detialView);
        imageCardView = (CardView) findViewById(id.cardViewDetail);
        mCommentRecyclerView = (RecyclerView) findViewById(id.commentRecyclerView);
        commentImageCardView = (CardView) findViewById(id.commentCardViewDetail);
        commentImageView = (ImageView) findViewById(id.commentDetialView);
        mCommentFab = (FloatingActionButton) findViewById(id.fabComment);
        mLikeButton = (ImageButton) findViewById(id.likeButton);

        relativeLayout = (RelativeLayout) findViewById(id.detailLayout);
        constraintLayout = (ConstraintLayout) findViewById(id.mainActivityLayout);
//        titleTxt.startAnimation(fade_in);

        //Receive Data
        mDatabaseIntent = FirebaseUtil.getBaseRef().child("posts");
        mPost_key = getIntent().getExtras().getString("uid");
        mDatabaseIntent.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                final String post_image = (String) dataSnapshot.child("imageURL").getValue();
                String post_uid = (String) dataSnapshot.child("uid").getValue();

                if (mUid.equals(post_uid)) {
                    //Open Dialog Fragment
                    imageView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            showDeleteDialog();
                            return false;
                        }
                    });
                    commentImageView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            showDeleteDialog();
                            return false;
                        }
                    });
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            imageCardView.setVisibility(View.INVISIBLE);
                            commentImageCardView.setVisibility(View.VISIBLE);
                        mCommentFab.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mCommentFab.setVisibility(View.VISIBLE);
                                AnimationUtil.setScaleAnimation(mCommentFab);
                            }
                        },500);
                    }
                });
                commentImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageCardView.setVisibility(View.VISIBLE);
                        commentImageCardView.setVisibility(View.INVISIBLE);
                        mCommentFab.setVisibility(View.INVISIBLE);
                    }
                });

                //Bind Data
                titleTxt.setText(post_title);
                Glide.with(DetailActivity.this)
                        .load(post_image)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.IMMEDIATE)
                        .into(imageView);

                Glide.with(DetailActivity.this)
                        .load(post_image)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .priority(Priority.HIGH)
                        .into(commentImageView);

                FirebaseUtil.getLikesRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Liked
                        if (dataSnapshot.child(mPost_key).hasChild(FirebaseUtil.getUid())){
                            mLikeButton.setImageResource(drawable.heart);

                        }else {
                            //Unliked
                            mLikeButton.setImageResource(drawable.heart_outline);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Open Comment Fragment
        mCommentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog();
            }
        });

        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProcessLike = true;
                    FirebaseUtil.getLikesRef().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (mProcessLike) {
                                    //Unliked
                                if (dataSnapshot.child(mPost_key).hasChild(mUid)) {
                                    FirebaseUtil.getLikesRef().child(mPost_key).child(mUid).removeValue();
                                    mProcessLike = false;
                                    snackbar = Snackbar.make(relativeLayout, "Unliked", Snackbar.LENGTH_SHORT);
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),color.DarkColor));
                                    snackbar.show();
                                    //Liked
                                } else {
                                    FirebaseUtil.getLikesRef().child(mPost_key).child(mUid).setValue("like");
                                    mProcessLike = false;
                                    snackbar = Snackbar.make(relativeLayout, "Liked!", Snackbar.LENGTH_SHORT);
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),color.DarkColor));
                                    snackbar.show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
        });
    }

    void showDeleteDialog() {
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        deleteDialogFragment.show(fragmentManager, "Delete Dialog Fragment");
    }

    void showCommentDialog() {
        CommentsFragment commentsFragment = new CommentsFragment();
        commentsFragment.show(fragmentManager, "Comments Fragment");
    }

    public void postComment() {

        snackbar = Snackbar.make(relativeLayout, "Comment Posted", Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),color.DarkColor));
        snackbar.show();
}

    public void doPositiveClick() {
        FirebaseUtil.getDeletePostRef().child(mPost_key).removeValue();
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
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