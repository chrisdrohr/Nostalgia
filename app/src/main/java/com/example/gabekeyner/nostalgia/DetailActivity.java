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
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String mUsername, mPhotoUrl, mUid;

    // Firebase instance variables
    private FirebaseRecyclerAdapter<Comment, MessageViewHolder>mFirebaseAdapter;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseLike, mDatabaseIntent, mDatabase;

    private Animation fade_in;
    private TextView titleTxt, imageViewText;
    private ImageView imageView, commentImageView;
    private String mPostKey = null;
    private CardView imageCardView, commentImageCardView;
    private ImageButton mLikeButton;
    private Boolean mProcessLike = false;
    private Boolean mProcessComment = false;
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
        mDatabaseIntent = FirebaseUtil.getBaseRef().child("posts");
        mPostKey = getIntent().getExtras().getString("post_key");


        SimpleDateFormat time = new SimpleDateFormat("dd/MM-hh:mm");
        final String mCurrentTimestamp = time.format(new Date());

        mDatabaseLike.keepSynced(true);
//        Toast.makeText(this, mCommentKey, Toast.LENGTH_SHORT).show();

//        if (mProcessComment == true) {
            Toast.makeText(DetailActivity.this, "true", Toast.LENGTH_SHORT).show();
            mFirebaseAdapter = new FirebaseRecyclerAdapter<Comment, MessageViewHolder>(
                    Comment.class,
                    layout.item_comment,
                    MessageViewHolder.class,
                    FirebaseUtil.getCommentsRef()) {

                @Override
                protected void populateViewHolder(MessageViewHolder viewHolder, Comment model, final int position) {
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
//        }
//            FirebaseUtil.getCommentsRef().addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
////                    if (mProcessComment) {
//                        //Comments id matches post
//                        if (dataSnapshot.child(mPostKey).getKey().equals(mPostKey)) {
//                            Toast.makeText(DetailActivity.this, "do match", Toast.LENGTH_SHORT).show();
//                            mProcessComment = true;
//                            //Comments id don't match post
//                        } else {
//                            Toast.makeText(DetailActivity.this, "don't match", Toast.LENGTH_SHORT).show();
//                            mProcessComment = false;
//                        }
//                    }
////                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

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
        imageViewText = (TextView) findViewById(id.titleTextView);
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

        mDatabaseIntent.child(mPostKey).addValueEventListener(new ValueEventListener() {
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
//                titleTxt.setText(post_title);
                imageViewText.setText(post_title);
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
                        if (dataSnapshot.child(mPostKey).hasChild(FirebaseUtil.getUid())){
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
                                if (dataSnapshot.child(mPostKey).hasChild(mUid)) {
                                    FirebaseUtil.getLikesRef().child(mPostKey).child(mUid).removeValue();
                                    mProcessLike = false;
                                    snackbar = Snackbar.make(relativeLayout, "Unliked", Snackbar.LENGTH_SHORT);
                                    View snackBarView = snackbar.getView();
                                    snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),color.DarkColor));
                                    snackbar.show();
                                    //Liked
                                } else {
                                    FirebaseUtil.getLikesRef().child(mPostKey).child(mUid).setValue("like");
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
        Bundle bundle = new Bundle();
        bundle.putString("postKey", getIntent().getExtras().getString("uid"));
        commentsFragment.setArguments(bundle);
        commentsFragment.show(fragmentManager, "Comments Fragment");
    }

    public void postComment() {

        snackbar = Snackbar.make(relativeLayout, "Comment Posted", Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),color.DarkColor));
        snackbar.show();
}

    public void doPositiveClick() {
        FirebaseUtil.getDeletePostRef().child(mPostKey).removeValue();
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