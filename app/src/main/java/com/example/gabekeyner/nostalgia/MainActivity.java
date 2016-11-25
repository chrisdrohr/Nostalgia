package com.example.gabekeyner.nostalgia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.StorageReference;

import static com.example.gabekeyner.nostalgia.R.menu.main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    public RecyclerView recyclerView;

    private DatabaseReference mDatabase;
    private DatabaseReference mChildRef;
    private StorageReference mStorage;
    private PostAdapter mPostAdapter;
    private Toolbar toolbar;

    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private String mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;
    private Context context;
    private Post model;
    private ImageView imageTransition, userImageView;
    private TextView userTextView;
    private ConstraintLayout constraintLayout;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    FloatingActionButton fab, fabPhoto, fabVideo, floatingActionButton1, floatingActionButton2, floatingActionButton3;
    Animation hide_fab, show_fab, show_fab2, show_fab3, rotate_anticlockwise, rotate_clockwise, stayhidden_fab;
    boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = FirebaseUtil.getUser().getUserName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
            }
        }

        constraintLayout = (ConstraintLayout) findViewById(R.id.mainActivityLayout);
//        // Initialize Firebase Remote Config.
//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//
//// Define Firebase Remote Config Settings.
//        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
//                new FirebaseRemoteConfigSettings.Builder()
//                        .setDeveloperModeEnabled(true)
//                        .build();
//
//// Define default config values. Defaults are used when fetched config values are not
//// available. Eg: if an error occurred fetching values from the server.
//        Map<String, Object> defaultConfigMap = new HashMap<>();
//        defaultConfigMap.put("friendly_msg_length", 10L);
//
//// Apply config settings and default values.
//        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
//        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
//
//// Fetch remote config.
//        fetchConfig();

        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());
        initViews();
        fabAnimations();
        fabClickable();
        fabPhoto.startAnimation(stayhidden_fab);
        fabVideo.startAnimation(stayhidden_fab);
        fabPhoto.setClickable(false);
        fabVideo.setClickable(false);

        fab.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickFab();
            }
        }, 2000);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //User Info display
        final View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        userImageView = (ImageView)headerLayout.findViewById(R.id.drawerImageView);
        userTextView = (TextView) headerLayout.findViewById(R.id.drawerNameTextView);
        userTextView.setText(mUsername);
        Glide.with(this)
                .load(mPhotoUrl)
                .centerCrop()
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImageView);


        ViewAnimator.animate(toolbar)
                .slideLeft()
                .duration(1000)
                .thenAnimate(fabPhoto, fabVideo, floatingActionButton1, floatingActionButton2, floatingActionButton3)
                .bounce()
                .duration(800)
                .start();



    }
//    // Fetch the config to determine the allowed length of messages.
//    public void fetchConfig() {
//        long cacheExpiration = 3600; // 1 hour in seconds
//        // If developer mode is enabled reduce cacheExpiration to 0 so that
//        // each fetch goes to the server. This should not be used in release
//        // builds.
//        if (mFirebaseRemoteConfig.getInfo().getConfigSettings()
//                .isDeveloperModeEnabled()) {
//            cacheExpiration = 0;
//        }
//        mFirebaseRemoteConfig.fetch(cacheExpiration)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Make the fetched config available via
//                        // FirebaseRemoteConfig get<type> calls.
//                        mFirebaseRemoteConfig.activateFetched();
//                        applyRetrievedLengthLimit();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // There has been an error fetching the config
//                        Log.w(TAG, "Error fetching config: " +
//                                e.getMessage());
//                        applyRetrievedLengthLimit();
//                    }
//                });
//    }
    /**
     * Apply retrieved length limit to edit text field.
     * This result may be fresh from the server or it may be from cached
     * values.
     */
//    mEditText = (EditText) findViewById(R.id.commentEditText);
//    private void applyRetrievedLengthLimit() {
//        Long friendly_msg_length =
//                mFirebaseRemoteConfig.getLong("friendly_msg_length");
//        mEditText.setFilters(new InputFilter[]{new
//                InputFilter.LengthFilter(friendly_msg_length.intValue())});
//        Log.d(TAG, "FML is: " + friendly_msg_length);
//    }

    private void fabAnimations() {
        //ANIMATION LAYOUTS
        hide_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_hide);
        show_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show);
        show_fab2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show2);
        show_fab3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show3);
        rotate_anticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        rotate_clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        stayhidden_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_stayhidden);

        //MY FLOATING ACTION BUTTONS
        fab = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        fabPhoto = (FloatingActionButton) findViewById(R.id.fabPhoto);
        fabVideo = (FloatingActionButton) findViewById(R.id.fabVideo);

    }

    private void fabClickable() {

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    floatingActionButton2.startAnimation(rotate_anticlockwise);

                    floatingActionButton1.startAnimation(hide_fab);
                    floatingActionButton3.startAnimation(hide_fab);

                    fabPhoto.startAnimation(show_fab2);
                    fabVideo.startAnimation(show_fab3);

                    fabPhoto.setClickable(true);
                    fabVideo.setClickable(true);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton1.setClickable(false);
                    floatingActionButton3.setClickable(false);

                    isOpen = false;

                } else {
                    fabPhoto.startAnimation(hide_fab);
                    fabVideo.startAnimation(hide_fab);

                    fabPhoto.setClickable(false);
                    fabVideo.setClickable(false);

                    floatingActionButton1.startAnimation(show_fab2);
                    floatingActionButton3.startAnimation(show_fab3);

                    floatingActionButton1.setClickable(true);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton3.setClickable(true);

                    isOpen = true;
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
//                    fab.startAnimation(rotate_anticlockwise);
                    ViewAnimator.animate(fab)
                            .rotation(0)
                            .duration(300)
                            .start();
                    floatingActionButton1.startAnimation(hide_fab);
                    floatingActionButton2.startAnimation(hide_fab);
                    floatingActionButton3.startAnimation(hide_fab);

                    fabPhoto.startAnimation(stayhidden_fab);
                    fabVideo.startAnimation(stayhidden_fab);

                    floatingActionButton1.setClickable(false);
                    floatingActionButton2.setClickable(false);
                    floatingActionButton3.setClickable(false);

                    isOpen = false;

                } else {
//                    fab.startAnimation(rotate_clockwise);
                    ViewAnimator.animate(fab)
                            .rotation(540)
                            .duration(300)
                            .start();
                    floatingActionButton1.startAnimation(show_fab2);
                    floatingActionButton2.startAnimation(show_fab);
                    floatingActionButton3.startAnimation(show_fab3);

                    fabPhoto.startAnimation(stayhidden_fab);
                    fabVideo.startAnimation(stayhidden_fab);

                    floatingActionButton1.setClickable(true);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton3.setClickable(true);

                    fabPhoto.setClickable(false);
                    fabVideo.setClickable(false);

                    isOpen = true;
                }
            }
        });
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.ACTIVITY_INTENTION, CameraActivity.TAKE_PHOTO);
                startActivity(intent);
            }
        });
        fabPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.ACTIVITY_INTENTION, CameraActivity.GALLERY_PICKER);
                finishAfterTransition();
                startActivity(intent);
            }
        });
        fabVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.ACTIVITY_INTENTION, CameraActivity.GALLERY_VIDEO_PICKER);
                startActivity(intent);
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.ACTIVITY_INTENTION, CameraActivity.VIDEO_SHOOTER);
                startActivity(intent);
            }
        });
    }

    private void clickFab() {
        fab.callOnClick();
    }
    //VIEWS
    private void initViews() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        manager.getGapStrategy();
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mChildRef = mDatabase.child("posts");
        mPostAdapter = new PostAdapter(Post.class, R.layout.card_view, Viewholder.class, mChildRef, getApplicationContext());
//        mPostAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeChanged(int positionStart, int itemCount) {
//                super.onItemRangeChanged(positionStart, itemCount);
//                int viewCount = mPostAdapter.getItemCount();
//                int lastVisiblePosition = manager.findLastCompletelyVisibleItemPositions(null)[0];
//                recyclerView.scrollToPosition(positionStart);
//                if (lastVisiblePosition == -1) ||
//                (positionStart >= (viewCount - 1) && lastVisiblePosition == (positionStart - 1))) {
//                    recyclerView.scrollToPosition(positionStart);
//                }
//            }
//        });
        recyclerView.setAdapter(mPostAdapter);
    }


    @Override
    public void onBackPressed() {
        // Handles the Navigation Drawer Opening / Closing
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        ViewAnimator.animate(toolbar)
//                .slideRight()
//                .duration(500)
//                .andAnimate(fab)
//                .slideBottom()
//                .duration(500)
//                .andAnimate(recyclerView)
//                .fadeOut()
//                .start();
//    }

    @Override
    public void onResume() {
        super.onResume();
        ViewAnimator.animate(toolbar)
                .slideLeft()
                .duration(300)
                .andAnimate(recyclerView,fab)
                .slideBottom()
                .duration(300)
                .thenAnimate(fabPhoto, fabVideo, floatingActionButton1, floatingActionButton2, floatingActionButton3)
                .bounce()
                .duration(300)
                .start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //LAYOUTS & ORIENTATIONS
        switch (id) {
//            case R.id.fresh_config_menu:
//                fetchConfig();
//                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            case R.id.linearViewVertical:
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
                break;
            case R.id.twoViewVertical:
                StaggeredGridLayoutManager mStaggered2VerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mStaggered2VerticalLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                recyclerView.setLayoutManager(mStaggered2VerticalLayoutManager);
                break;
            case R.id.staggeredViewVertical:
                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                mStaggeredVerticalLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
            // Handle the pulling from gallery action

         if (id == R.id.nav_create_group) {
            // Handle the New Grouup action  action
        } else if (id == R.id.nav_first_label) {
            // Handle the added Group Label action
        } else if (id == R.id.nav_share) {
            // Handle the share action

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void transition (View view) {
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, imageTransition, "imageTransition");
//        Intent i = new Intent(MainActivity.this, DetailActivity.class);
//        startActivity(i, options.toBundle());
//    }
}
