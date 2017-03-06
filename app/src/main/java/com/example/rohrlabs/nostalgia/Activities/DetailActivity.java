package com.example.rohrlabs.nostalgia.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rohrlabs.nostalgia.DialogFragments.DeleteDialogFragment;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.R;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareButton;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sloop.fonts.FontsManager;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

import static com.example.rohrlabs.nostalgia.R.menu.detail;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressBar mProgressBar;
    private RecyclerView mCommentRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FloatingActionButton mSendFab, mCommentFab;
    private RelativeLayout relativeLayout;
    private ConstraintLayout constraintLayout;
    private Snackbar snackbar;
    private Context mContext;
    private Toolbar toolbar;
    private String mUsername, mPhotoUrl, mUid, commentPath, timeStamp;

    // Firebase instance variables
    private DatabaseReference mDatabaseLike, mDatabaseIntent;
    private TextView imageViewText;
    private ImageView imageView, commentImageView;
//    public String mPostKey;
    public static String post_image, post_title, mPostKey;
    private CardView imageCardView;
    private ImageButton mLikeButton;
    private Boolean mProcessLike = false;
    private ImageButton mShareButton;
    private ShareButton mFbShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView (R.layout.detail_view);
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        FontsManager.initFormAssets(this, "fonts/Roboto-Regular.ttf");
        FontsManager.changeFonts(this);

        mContext = getApplicationContext();
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mDatabaseLike = FirebaseUtil.getLikesRef();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseIntent = FirebaseUtil.getPostRef();
        mPostKey = getIntent().getExtras().getString("mPostKey");
        mDatabaseLike.keepSynced(true);
        imageViewText = (TextView) findViewById(R.id.titleTextView);
        imageView = (ImageView) findViewById(R.id.detialView);
        imageCardView = (CardView) findViewById(R.id.cardViewDetail);
        mCommentRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewCommentList);
        commentImageView = (ImageView) findViewById(R.id.commentDetialView);
        mLikeButton = (ImageButton) findViewById(R.id.likeButton);
        mShareButton = (ImageButton) findViewById(R.id.shareButton);
        relativeLayout = (RelativeLayout) findViewById(R.id.detailLayout);
        mFbShareButton = (ShareButton) findViewById(R.id.fbShareButton);

        mShareButton.setOnClickListener(this);
        mLikeButton.setOnClickListener(this);

                databaseListener();
    }

    @Override
    protected void onResume() {
        ViewAnimator.animate(toolbar)
                .alpha(1,1)
                .start();
        ViewAnimator.animate(mCommentRecyclerView)
                .translationX(1000,0)
                .duration(100)
                .andAnimate(imageCardView)
                .alpha(0,0)
                .thenAnimate(imageCardView)
                .alpha(1,1)
                .translationX(1000,0)
                .descelerate()
                .duration(100)
                .start();
        super.onResume();
    }

    void showDeleteDialog() {
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        deleteDialogFragment.show(getFragmentManager(), "Delete Dialog Fragment");
    }

//    public void doPositiveClick() {
//        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
//        startActivity(intent);
//        FirebaseUtil.getDeletePostRef().child(mPostKey).removeValue();
//        finish();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.viewPhoto:
                ViewAnimator.animate(imageCardView)
                        .translationY(-500,0)
                        .alpha(0,1)
                        .duration(200)
                        .andAnimate(imageView)
                        .translationY(-500,0)
                        .alpha(0,1)
                        .duration(200)
                        .start();
                imageCardView.setVisibility(View.VISIBLE);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fbShareButton:
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
//                ShareDialog shareDialog;
//                shareDialog = new ShareDialog(this);
//                view = findViewById(R.id.imageView).getRootView();
//                view.setDrawingCacheEnabled(true);
//                mBitmap = Bitmap.createBitmap(view.getDrawingCache());
//                view.destroyDrawingCache();
//                SharePhoto photo = new SharePhoto.Builder().setBitmap(mBitmap).setCaption(post_title).build();
//                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
//                mFbShareButton.setShareContent(content);
//                mFbShareButton.performClick();
//                shareDialog.show(content);
                break;
            case R.id.likeButton:
                likeFuntion();
                break;
            default:
        }
    }

//    public void setShareIntent(Intent shareIntent) {
//        if (mShareActionProvider != null) {
//            mShareActionProvider.setShareIntent(shareIntent);
//        }
//    }
    public void likeFuntion() {
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

    public void databaseListener() {
        mDatabaseIntent.child(mPostKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post_title = (String) dataSnapshot.child("title").getValue();
                post_image = (String) dataSnapshot.child("imageURL").getValue();
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
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAnimator.animate(imageView)
                                .translationY(0,-500)
                                .alpha(1,0)
                                .duration(200)
                                .andAnimate(imageCardView)
                                .alpha(1,0)
                                .duration(200)
                                .start();
                        imageView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageCardView.setVisibility(View.INVISIBLE);
                            }
                        },500);

                    }
                });

                //Bind Data
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
                        .bitmapTransform(new GrayscaleTransformation(DetailActivity.this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.NORMAL)
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
    }

}