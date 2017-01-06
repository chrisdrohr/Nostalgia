package com.example.gabekeyner.nostalgia.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gabekeyner.nostalgia.Adapters.NavGroupsAdapter;
import com.example.gabekeyner.nostalgia.DialogFragments.GroupFragment;
import com.example.gabekeyner.nostalgia.DialogFragments.UploadFragment;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.ObjectClasses.User;
import com.example.gabekeyner.nostalgia.R;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sloop.fonts.FontsManager;

import static com.example.gabekeyner.nostalgia.Adapters.NavGroupsAdapter.groupPhoto;
import static com.example.gabekeyner.nostalgia.R.menu.main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final int RC_SIGN_IN = 0;

    public RecyclerView recyclerView;
    private CardView cardView;
    private Toolbar toolbar;
    private String mUsername, mPhotoUrl, mUid, userKey;
    private Boolean mProcessUser = true;
    private DatabaseReference mDatabaseUserExists;
    private ImageView userImageView, mainBg;
    private TextView userTextView, mTitle;
    private GoogleApiClient mGoogleApiClient;
    private NavigationView navigationView;
    private Boolean mProcessUserExists = false;
    private DrawerLayout drawer;
    private Snackbar snackbar;
    private RelativeLayout relativeLayout;
    private ConstraintLayout constraintLayout;
    private SharedPreferences sharedPreferences;
    private static String groupKey = "groupKey";
    private Uri mMediaUri;
    private ProgressBar progressBar;
    private final static int SELECT_PHOTO = 0;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private StorageReference mStorageReference;
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();

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
        mStorageReference = mFirebaseStorage.getReference().child("posts");
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = FirebaseUtil.getUser().getUserName();
            mDatabaseUserExists = FirebaseUtil.getUserExistsRef();
            mDatabaseUserExists.keepSynced(true);
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
            }
        }
        mUid = FirebaseUtil.getUid();
        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());
        fabAnimations();
        fabClickable();
        checkUser();

        fab.setVisibility(View.INVISIBLE);

        fab.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimator.animate(floatingActionButton2)
                        .bounceOut()
                        .descelerate()
                        .duration(100)
                        .thenAnimate(fabPhoto)
                        .bounceOut()
                        .descelerate()
                        .duration(100)
                        .thenAnimate(fabVideo)
                        .bounceOut()
                        .descelerate()
                        .duration(100)
                        .start();
                isOpen = false;
                fab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fab.setVisibility(View.VISIBLE);
                        ViewAnimator.animate(fab)
                                .slit()
                                .duration(500)
                                .start();
                    }
                }, 500);

            }
        }, 500);

        cardView = (CardView) findViewById(R.id.cardView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.content_main);
        constraintLayout = (ConstraintLayout) findViewById(R.id.mainActivityLayout);

        mainBg = (ImageView) findViewById(R.id.mainBg);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(NavGroupsAdapter.groupName);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
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

        Glide.with(this)
                .load(groupPhoto)
                .centerCrop()
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mainBg);
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
//        fab = (FloatingActionButton) findViewById(fab);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
//        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        fabPhoto = (FloatingActionButton) findViewById(R.id.fabPhoto);
        fabVideo = (FloatingActionButton) findViewById(R.id.fabVideo);

    }

    private void fabClickable() {


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
//                    fab.startAnimation(rotate_anticlockwise);
                    ViewAnimator.animate(fab)
                            .rotation(0)
                            .duration(300)
                            .andAnimate(floatingActionButton2, fabPhoto, fabVideo)
                            .bounceOut()
                            .duration(300)
                            .start();

                    isOpen = false;

                } else {
//                    fab.startAnimation(rotate_clockwise);
                    ViewAnimator.animate(fab)
                            .rotation(540)
                            .duration(300)
                            .andAnimate(floatingActionButton2, fabVideo, fabPhoto)
                            .bounceIn()
                            .duration(300)
                            .start();

                    isOpen = true;
                }
            }
        });

        fabPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PHOTO);
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
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGroupsActivity();
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
//            mMediaUri = data.getData();
//            groupBg.setImageURI(mMediaUri);
//            progressBar.setVisibility(View.VISIBLE);
//            final StorageReference photoRef = mStorageReference.child(mMediaUri.getLastPathSegment());
//            photoRef.putFile(mMediaUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(MainActivity.this, "Photo uploaded!", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.INVISIBLE);
//
//                }
//            });
//        }
//    }

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
    protected void onStart() {
//        SharedPreferences sharedPreferences = getSharedPreferences("groupKey", Context.MODE_PRIVATE);
//        groupKey = sharedPreferences.getString("groupKey", "");
//        if (groupKey.equals("")){
//            Toast.makeText(this, "data not found", Toast.LENGTH_SHORT).show();
//        }
//        else {
//
//            Intent intent = new Intent(this, NavGroupsAdapter.class);
//            intent.putExtra(sharedPreferences.getString("groupKey", groupKey), "groupKey");
//            this.sendBroadcast(intent);
//            this.startActivity(intent);
//            Toast.makeText(this, "data found" + groupKey, Toast.LENGTH_SHORT).show();
//        }
//        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
//        drawer.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                drawer.openDrawer(Gravity.LEFT);
//            }
//        },1000);
        super.onStart();
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
    protected void onStop() {
//        Intent intent = getIntent();
//        groupKey = intent.getStringExtra("groupKey");
//        SharedPreferences sharedPreferences = getSharedPreferences("groupKey", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("groupKey", groupKey);
//        editor.apply();
//        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        super.onStop();
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
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                mUsername = ANONYMOUS;
                return true;
//            case R.id.linearViewVertical:
//                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
//                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
//                mLinearLayoutManagerVertical.setItemPrefetchEnabled(false);
//                break;
//            case R.id.twoViewVertical:
//                StaggeredGridLayoutManager mStaggered2VerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(mStaggered2VerticalLayoutManager);
//                mStaggered2VerticalLayoutManager.setItemPrefetchEnabled(false);
//                break;
//            case R.id.staggeredViewVertical:
//                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
//                mStaggeredVerticalLayoutManager.setItemPrefetchEnabled(false);
//                break;
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

    public void openUploadFragment () {
        UploadFragment uploadFragment = new UploadFragment();
        uploadFragment.show(getFragmentManager(), "Upload Fragment");
    }

        public void checkUser () {
            mProcessUserExists = true;
        FirebaseUtil.getBaseRef().child("users/exists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mProcessUserExists) {
                    if (dataSnapshot.hasChild(mUid)) {
//                        snackbar = Snackbar.make(constraintLayout, "Welcome back!", Snackbar.LENGTH_SHORT);
//                        View snackBarView = snackbar.getView();
//                        snackBarView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.DarkColor));
//                        snackbar.show();

//                        drawer.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                drawer.openDrawer(Gravity.LEFT);
//                            }
//                        },700);

                        mProcessUserExists = false;
                } else {
                    addUser();
                        FirebaseUtil.getUserExistsRef().push().setValue(mUid);
                    mProcessUserExists = false;

                }
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
                    FirebaseUtil.getUserRef().removeEventListener(this);
                    snackbar = Snackbar.make(constraintLayout, "Create a group to get started!", Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.DarkColor));
                    snackbar.show();

                    drawer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            drawer.openDrawer(Gravity.LEFT);
                        }
                    },1000);

                    mProcessUser = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
