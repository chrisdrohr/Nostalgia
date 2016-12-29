package com.example.gabekeyner.nostalgia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gabekeyner.nostalgia.Adapters.NavGroupsAdapter;
import com.example.gabekeyner.nostalgia.DialogFragments.GroupFragment;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.User;
import com.example.gabekeyner.nostalgia.R;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sloop.fonts.FontsManager;

import static com.example.gabekeyner.nostalgia.R.menu.main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public RecyclerView recyclerView;
    private CardView cardView;
    private Toolbar toolbar;
    private String mUsername, mPhotoUrl, mUid, userKey;
    private Boolean mProcessUser = true;
    private DatabaseReference mDatabaseUserExists;
    public static final String ANONYMOUS = "anonymous";
    private ImageView userImageView;
    private TextView userTextView, mTitle;
    private GoogleApiClient mGoogleApiClient;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    FloatingActionButton fab, fabPhoto, fabVideo, floatingActionButton1, floatingActionButton2, floatingActionButton3;
    Animation hide_fab, show_fab, show_fab2, show_fab3, rotate_anticlockwise, rotate_clockwise, stayhidden_fab;
    boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMainList);

        FontsManager.initFormAssets(this, "fonts/Roboto-Regular.ttf");
        mTitle = (TextView) findViewById(R.id.textView);
        FontsManager.changeFonts(mTitle);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseUserExists = FirebaseUtil.getUserExistsRef();
        mDatabaseUserExists.keepSynced(true);
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
        mUid = FirebaseUtil.getUid();
        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());
        fabAnimations();
        fabClickable();
        checkUser();
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

//        layoutManager = new LinearLayoutManager(context);
        cardView = (CardView) findViewById(R.id.cardView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(NavGroupsAdapter.groupName);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //user Info display
        final View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        userImageView = (ImageView)headerLayout.findViewById(R.id.drawerImageView);
        userTextView = (TextView) headerLayout.findViewById(R.id.drawerNameTextView);
        userTextView.setText(mUsername);
        FontsManager.changeFonts(userTextView);
        Glide.with(this)
                .load(mPhotoUrl)
                .centerCrop()
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImageView);
    }

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

    @Override
    public void onBackPressed() {
        // Handles the Navigation Drawer Opening / Closing
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewAnimator.animate(toolbar)
//                .slideTop()
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
    protected void onPause() {
//        Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
        ViewAnimator.animate(toolbar, recyclerView)
                .alpha(1,1)
                .duration(1000)
                .start();
        super.onPause();
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
                startActivity(new Intent(this, SignInActivity.class));
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                return true;
            case R.id.linearViewVertical:
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
                mLinearLayoutManagerVertical.setItemPrefetchEnabled(false);
                break;
            case R.id.twoViewVertical:
                StaggeredGridLayoutManager mStaggered2VerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mStaggered2VerticalLayoutManager);
                mStaggered2VerticalLayoutManager.setItemPrefetchEnabled(false);
                break;
            case R.id.staggeredViewVertical:
                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
                mStaggeredVerticalLayoutManager.setItemPrefetchEnabled(false);
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

         if (id == R.id.nav_create_group) {
            // Handle the New Grouup action  action
             openGroupsActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void openGroupsActivity () {
        GroupFragment groupFragment = new GroupFragment();
        groupFragment.show(getFragmentManager(), "Group Fragment");
    }

        public void checkUser () {
            FirebaseUtil.getUserExistsRef().setValue(FirebaseUtil.getUid());
        FirebaseUtil.getUserExistsRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals(mUid)) {
                    //TODO: fix user add
//                    addUser();
                }else {
                    addUser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addUser () {
        mProcessUser = true;
        mUsername = FirebaseUtil.getUserName();
        FirebaseUtil.getUserRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mProcessUser) {
//                    if new user
                    mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
                    mUsername = FirebaseUtil.getUser().getUserName();
                    mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    User user = new User(
                            mUsername,
                            mPhotoUrl,
                            mUid,
                            null,
                            null
                    );
                    FirebaseUtil.getUserRef().push().setValue(user);
                    Toast.makeText(MainActivity.this, "Hello " + mUsername, Toast.LENGTH_LONG).show();
                    mProcessUser = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
