package com.example.rohrlabs.nostalgia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.Adapters.ViewPagerAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.Fragments.ChatFragment;
import com.example.rohrlabs.nostalgia.Fragments.GroupFragment;
import com.example.rohrlabs.nostalgia.Fragments.PostFragment;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.example.rohrlabs.nostalgia.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sloop.fonts.FontsManager;

import static com.example.rohrlabs.nostalgia.R.id.textView;
import static com.example.rohrlabs.nostalgia.R.menu.main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

//    private static final int RC_SIGN_IN = 0;

    public RecyclerView recyclerView;
    private CardView mCardViewGroupMembers;
    private Toolbar toolbar;
    private String mUsername, mPhotoUrl, mUid, userKey;
    private Boolean mProcessUser = true;
    private DatabaseReference mDatabaseUserExists;
    private ImageView userImageView, mainBg;
    private TextView userTextView, mTitle, mTextViewLabel;
    private GoogleApiClient mGoogleApiClient;
    private NavigationView navigationView;
    private Boolean mProcessUserExists = false;
    private DrawerLayout drawer;
//    private Snackbar snackbar;
//    private View snackBarView;
    private RelativeLayout relativeLayout, fabLayout;
//    private ConstraintLayout constraintLayout;
//    private SharedPreferences sharedPreferences;
    private static String mGroupKey = "mGroupKey";
//    private Uri mMediaUri;
//    private ProgressBar progressBar;
//    private final static int SELECT_PHOTO = 0;
    public static boolean mFbSignIn, mGSignIn = false;
    private RelativeLayout mLayoutDeleteGroup;
//    private FrameLayout mLayoutGroupFragment;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private StorageReference mStorageReference;
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    private FloatingActionButton fabPhoto, fabVideo,fabGroup, mFabGroupMembers, mFabGroupDelete, mFabGroupCancel;
    private AccessToken mAccessToken;
    private Profile mProfile;
    private ProfileTracker mProfileTracker;
    boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                mAccessToken = AccessToken.getCurrentAccessToken();
            }
        });
        AppEventsLogger.activateApp(this.getApplicationContext());
        setContentView(R.layout.app_bar_main);

//        mProfile.getCurrentProfile();
//        Toast.makeText(this, mProfile.toString(), Toast.LENGTH_LONG).show();

//        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMainList);

        FontsManager.initFormAssets(this, "fonts/Roboto-Regular.ttf");
        mTitle = (TextView) findViewById(textView);
        FontsManager.changeFonts(mTitle);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("posts");
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

//        if (SignInActivity.mFbSignIn && !SignInActivity.mGSignIn) {
//            Toast.makeText(this, "FB" + SignInActivity.mFbSignIn, Toast.LENGTH_SHORT).show();
//
//        } if (SignInActivity.mGSignIn && SignInActivity.mFbSignIn) {
//            Toast.makeText(this, "google" + SignInActivity.mGSignIn, Toast.LENGTH_SHORT).show();
//            mTextViewPostUserName = FirebaseUtil.getUser().getUserName();
//            mDatabaseUserExists = FirebaseUtil.getUserExistsRef();
//            mDatabaseUserExists.keepSynced(true);
//            mDatabaseUserExists = FirebaseUtil.getUserExistsRef();
//            mDatabaseUserExists.keepSynced(true);
//            mUid = FirebaseUtil.getUid();
//            if (mFirebaseUser.getPhotoUrl() != null) {
//                mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
//            }
//        }else {
//            startActivity(new Intent(this, SignInActivity.class));
//            finish();
//        }

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = FirebaseUtil.getUser().getUserName();
            mDatabaseUserExists = FirebaseUtil.getUserExistsRef();
            mDatabaseUserExists.keepSynced(true);
            mUid = FirebaseUtil.getUid();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
            }
        }

        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());
//        initializeViews();
//        fabClickable();
        checkUser();

        mFabGroupMembers = (FloatingActionButton) findViewById(R.id.fabExit);
        mLayoutDeleteGroup = (RelativeLayout) findViewById(R.id.layout_deleteGroup);
        mFabGroupDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        mFabGroupCancel = (FloatingActionButton) findViewById(R.id.fabCancelGroupDelete);
        mCardViewGroupMembers = (CardView) findViewById(R.id.cardViewGroupMembers);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        relativeLayout = (RelativeLayout) findViewById(R.id.content_main);
//        fabLayout = (RelativeLayout) findViewById(R.id.fabLayout);
//        mainBg = (ImageView) findViewById(R.id.mainBg);

        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(NavGroupsAdapter.groupName);

//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);

//        toggle.syncState();
        mViewPager = (ViewPager) findViewById(R.id.viewPagerContainer);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        mViewPagerAdapter.addFragments(new GroupFragment(), "Groups");
        mViewPagerAdapter.addFragments(new PostFragment(), "Posts");
        mViewPagerAdapter.addFragments(new ChatFragment(), "Chat");

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Toast.makeText(MainActivity.this, "u", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });





//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        firstOpen();
//        userInfoDisplay();







    }

    private void userInfoDisplay () {
        final View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        userImageView = (ImageView)headerLayout.findViewById(R.id.drawerImageView);
        userTextView = (TextView) headerLayout.findViewById(R.id.drawerNameTextView);
        userTextView.setText(mUsername);
        FontsManager.changeFonts(userTextView);
        Glide.with(this)
                .load(mPhotoUrl)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImageView);

//        Glide.with(this)
//                .load(groupPhoto)
//                .centerCrop()
//                .thumbnail(0.5f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(mainBg);
    }
    private void firstOpen() {
//        Toast.makeText(this, NavGroupsAdapter.mGroupKey, Toast.LENGTH_SHORT).show();
//        if (NavGroupsAdapter.mGroupKey.equals(mGroupKey)){
//            drawer.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                drawer.openDrawer(Gravity.LEFT);
//            }
//        },1000);
//        } else {
            ViewAnimator.animate(fabLayout)
                    .slideBottom()
                    .duration(600)
                    .start();
            fabLayout.setVisibility(View.VISIBLE);
            ViewAnimator.animate(fabGroup, fabPhoto, fabVideo)
                    .alpha(0,0)
                    .duration(500)
                    .thenAnimate(fabGroup)
                    .newsPaper()
                    .descelerate()
                    .duration(150)
                    .thenAnimate(fabPhoto)
                    .newsPaper()
                    .descelerate()
                    .duration(150)
                    .thenAnimate(fabVideo)
                    .newsPaper()
                    .descelerate()
                    .duration(150)
                    .start();
//        }
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
        ViewAnimator.animate(fabLayout)
                .slideBottom()
                .duration(500)
                .start();
    }

    @Override
    protected void onPause() {
        ViewAnimator.animate(toolbar, recyclerView)
                .alpha(1,1)
                .duration(1000)
                .start();
        super.onPause();
    }

    @Override
    protected void onStop() {
        ViewAnimator.animate(fabLayout)
                .fadeOut()
                .duration(100)
                .start();
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
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                return true;
            case R.id.group_member_menu:
                groupMembers();
                return true;
            case R.id.delete_group_menu:
                deleteGroup();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void groupMembers () {
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        mFabGroupMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAnimator.animate(mCardViewGroupMembers)
                        .fadeOut()
                        .descelerate()
                        .duration(300)
                        .start();
                mCardViewGroupMembers.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCardViewGroupMembers.setVisibility(View.GONE);
                    }
                },300);
            }
        });
        if (!mCardViewGroupMembers.isShown()){
            ViewAnimator.animate(mCardViewGroupMembers)
                    .newsPaper()
                    .descelerate()
                    .duration(300)
                    .start();
            mCardViewGroupMembers.setVisibility(View.VISIBLE);
        } else {
            ViewAnimator.animate(mCardViewGroupMembers)
                    .fadeOut()
                    .descelerate()
                    .duration(300)
                    .start();
            mCardViewGroupMembers.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCardViewGroupMembers.setVisibility(View.GONE);
                }
            },300);

        }
    }

    public void deleteGroup () {
        mGroupKey = GroupsAdapter.mGroupKey;
        mFabGroupMembers.setVisibility(View.GONE);
        mFabGroupDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Group Deleted", Toast.LENGTH_SHORT).show();
                FirebaseUtil.getGroupRef().child(mGroupKey).removeValue();
                drawer.openDrawer(Gravity.LEFT);
                mCardViewGroupMembers.setVisibility(View.GONE);
            }
        });
        mFabGroupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAnimator.animate(mCardViewGroupMembers)
                        .fadeOut()
                        .descelerate()
                        .duration(300)
                        .start();
                mCardViewGroupMembers.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCardViewGroupMembers.setVisibility(View.GONE);
                        mLayoutDeleteGroup.setVisibility(View.GONE);
                    }
                },300);
            }
        });
        if (!mCardViewGroupMembers.isShown()){
            ViewAnimator.animate(mCardViewGroupMembers)
                    .newsPaper()
                    .descelerate()
                    .duration(300)
                    .start();
            mCardViewGroupMembers.setVisibility(View.VISIBLE);
            mLayoutDeleteGroup.setVisibility(View.VISIBLE);
        } else {
            ViewAnimator.animate(mCardViewGroupMembers)
                    .fadeOut()
                    .descelerate()
                    .duration(300)
                    .start();
            mCardViewGroupMembers.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCardViewGroupMembers.setVisibility(View.GONE);
                    mLayoutDeleteGroup.setVisibility(View.GONE);
                }
            },300);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
         if (id == R.id.nav_create_group) {
            // Handle the New Grouup action  action
//             openGroupsActivity();
        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void openGroupsActivity () {
//        GroupFragment groupFragment = new GroupFragment();
//        groupFragment.show(getFragmentManager(), "Group Fragment");
//    }
//
//    public void openUploadFragment () {
//        UploadFragment uploadFragment = new UploadFragment();
//        uploadFragment.show(getFragmentManager(), "Upload Fragment");
//    }

        public void checkUser () {
            mProcessUserExists = true;
        FirebaseUtil.getBaseRef().child("users/exists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mProcessUserExists) {
                    if (dataSnapshot.hasChild(mUid)) {
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
//                    snackbar = Snackbar.make(constraintLayout, "Create a group to get started!", Snackbar.LENGTH_LONG);
//                    View snackBarView = snackbar.getView();
//                    snackBarView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.DarkColor));
//                    snackbar.show();

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
