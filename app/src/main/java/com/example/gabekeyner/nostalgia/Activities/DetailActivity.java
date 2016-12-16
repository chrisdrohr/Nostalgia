package com.example.gabekeyner.nostalgia.Activities;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.gabekeyner.nostalgia.DialogFragments.CommentsFragment;
import com.example.gabekeyner.nostalgia.DialogFragments.DeleteDialogFragment;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.Group;
import com.example.gabekeyner.nostalgia.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sloop.fonts.FontsManager;

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

    private ProgressBar mProgressBar;
    private RecyclerView mCommentRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mSendFab, mCommentFab;
    private RelativeLayout relativeLayout;
    private ConstraintLayout constraintLayout;
    private Snackbar snackbar;
    private Typeface typeface;
    private String mUsername, mPhotoUrl, mUid, commentPath, timeStamp;

    // Firebase instance variables
    private FirebaseRecyclerAdapter<Group.Comment, MessageViewHolder> mFirebaseAdapter;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseLike, mDatabaseIntent, mDatabase;

    private Animation fade_in, fade_out, fade_up;
    private TextView titleTxt, imageViewText;
    private ImageView imageView, commentImageView;
    private String mPostKey = null;
    private CardView imageCardView, commentImageCardView;
    private ImageButton mLikeButton;
    private Boolean mProcessLike = false;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);



        FontsManager.initFormAssets(this, "fonts/Roboto-Regular.ttf");
        FontsManager.changeFonts(this);

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
        mDatabaseIntent = FirebaseUtil.getBaseRef().child("posts");
        mPostKey = getIntent().getExtras().getString("post_key");

        mDatabaseLike.keepSynced(true);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Group.Comment, MessageViewHolder>(
                Group.Comment.class,
                R.layout.item_comment,
                MessageViewHolder.class,
                FirebaseUtil.getCommentsRef().child(mPostKey)) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Group.Comment model, final int position) {
                viewHolder.commentTextView.setText(model.getText());
                viewHolder.commentAutoTypeTextView.setTextAutoTyping(mUsername);
                viewHolder.commentTimestampAutoTextView.setTextAutoTyping(model.getTimestamp());
                viewHolder.commentTimestampAutoTextView.setDecryptionSpeed(150);
                viewHolder.commentAutoTypeTextView.setTypingSpeed(50);
                FontsManager.changeFonts(viewHolder.commentTextView);
                FontsManager.changeFonts(viewHolder.commentNameTextView);

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

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_detail);
        fade_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_up);

        titleTxt = (TextView) findViewById(R.id.commentDetailTitle);
        imageViewText = (TextView) findViewById(R.id.titleTextView);
        imageView = (ImageView) findViewById(R.id.detialView);
        imageCardView = (CardView) findViewById(R.id.cardViewDetail);
        mCommentRecyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        commentImageCardView = (CardView) findViewById(R.id.commentCardViewDetail);
        commentImageView = (ImageView) findViewById(R.id.commentDetialView);
        mCommentFab = (FloatingActionButton) findViewById(R.id.fabComment);
        mSendFab = (FloatingActionButton) findViewById(R.id.fabCommentSwitch);
        mLikeButton = (ImageButton) findViewById(R.id.likeButton);
        relativeLayout = (RelativeLayout) findViewById(R.id.detailLayout);
        constraintLayout = (ConstraintLayout) findViewById(R.id.mainActivityLayout);
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
                        ViewAnimator.animate(imageView)
                                .translationY(0,-500)
                                .alpha(1,0)
                                .duration(200)
                                .andAnimate(titleTxt)
                                .translationX(0,-800)
                                .duration(200)
                                .andAnimate(imageCardView)
                                .alpha(1,0)
                                .duration(200)
                                .andAnimate(commentImageView)
                                .alpha(0,1)
                                .duration(200)
                                .andAnimate(mCommentRecyclerView)
                                .translationX(-600,0)
                                .duration(200)
                                .start();
                        ViewAnimator.animate(mCommentFab, mSendFab)
                                .rollIn()
                                .bounceIn()
                                .duration(800)
                                .thenAnimate(mLikeButton)
                                .flash()
                                .duration(400)
                                .start();
                        imageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageCardView.setVisibility(View.INVISIBLE);
                            }
                        },500);

                    }
                });
                commentImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ViewAnimator.animate(imageCardView)
                                .translationY(-500,0)
                                .alpha(0,1)
                                .duration(200)

                                .andAnimate(imageView)
                                .translationY(-500,0)
                                .alpha(0,1)
                                .duration(200)

                                .andAnimate(titleTxt)
                                .bounceIn()
                                .duration(200)

                                .andAnimate(mCommentFab, mSendFab)
                                .rollOut()
                                .duration(200)

                                .andAnimate(commentImageView)
                                .alpha(1,0)
                                .duration(300)

                                .andAnimate(mCommentRecyclerView)
                                .translationX(0,-600)
                                .start();

                        imageCardView.setVisibility(View.VISIBLE);
//                        mCommentFab.setVisibility(View.INVISIBLE);
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
                            mLikeButton.setImageResource(R.drawable.heart);

                        }else {
                            //Unliked
                            mLikeButton.setImageResource(R.drawable.heart_outline);
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
                                snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkColor));
                                snackbar.show();
                                //Liked
                            } else {
                                FirebaseUtil.getLikesRef().child(mPostKey).child(mUid).setValue("like");
                                mProcessLike = false;
                                snackbar = Snackbar.make(relativeLayout, "Liked!", Snackbar.LENGTH_SHORT);
                                View snackBarView = snackbar.getView();
                                snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkColor));
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
        bundle.putString("postKey", getIntent().getExtras().getString("post_key"));
        commentsFragment.setArguments(bundle);
        commentsFragment.show(fragmentManager, "Comments Fragment");
    }

    public void postComment() {

        snackbar = Snackbar.make(relativeLayout, "Comment Posted", 1000);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.DarkColor));
        snackbar.show();
        ViewAnimator.animate(mCommentFab, mSendFab)
                .tada()
                .duration(1000)
                .andAnimate(mCommentFab, mSendFab)
                .translationY(0,-200)
                .duration(200)
                .thenAnimate(mCommentFab)
                .scale(1,0)
                .duration(100)
                .thenAnimate(mSendFab)
                .startDelay(300)
                .translationX(0,500)
                .descelerate()
                .duration(600)
                .thenAnimate(mCommentFab, mSendFab)
                .translationY(-200, 0)
                .duration(800)
                .thenAnimate(mCommentFab)
                .bounceIn()
                .duration(650)
                .start();
    }

    public void doPositiveClick() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        FirebaseUtil.getDeletePostRef().child(mPostKey).removeValue();
        finish();
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
