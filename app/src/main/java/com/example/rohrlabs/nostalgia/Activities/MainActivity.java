package com.example.rohrlabs.nostalgia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.Adapters.ViewPagerAdapter;
import com.example.rohrlabs.nostalgia.Firebase.FirebaseUtil;
import com.example.rohrlabs.nostalgia.FragmentUtils;
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

public class MainActivity extends AppCompatActivity {

//    private static final int RC_SIGN_IN = 0;

    public RecyclerView recyclerView;
    private CardView mCardViewGroupMembers;
    private Toolbar toolbar;
    private String mUsername, mPhotoUrl, mUid, userKey, mGroupName;
    private Boolean mProcessUser = true;
    private DatabaseReference mDatabaseUserExists;
    private ImageView userImageView, mImageViewMainBg;
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
    private String mGroupKey = null, mGroupRef, mGroupUserRef;
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
    private DatabaseReference mGroupExistsRef;
    private StorageReference mStorageReference;
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    private FloatingActionButton fabPhoto, fabVideo,fabGroup, mFabGroupMembers, mFabGroupDelete, mFabGroupCancel;
    private AccessToken mAccessToken;
    private Profile mProfile;
    private ProfileTracker mProfileTracker;
    boolean isOpen = true;
    private FrameLayout mLayoutGroupMembers;


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

        checkUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mGroupName = GroupsAdapter.groupName;
        mGroupKey = GroupsAdapter.mGroupKey;
//        mCheckGroupDeleteKey = DeleteGroupDialogFragment.mCheckGroupDeleteKey;

        setSupportActionBar(toolbar);
        if (mGroupName != null) {
            getSupportActionBar().setTitle(GroupsAdapter.groupName);
        }else {
            getSupportActionBar().setTitle("Nostalgia");
        }

        mViewPager = (ViewPager) findViewById(R.id.viewPagerContainer);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), this);
        mViewPagerAdapter.addFragments(new GroupFragment(), "");

        if (mGroupKey != null && mGroupKey != "mGroupKey") {
            mViewPagerAdapter.addFragments(new PostFragment(), "");
            mViewPagerAdapter.addFragments(new ChatFragment(), "");
            getPostTab();
            invalidateOptionsMenu();
        }
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mGroupKey != null && mGroupKey != "mGroupKey") {
            menu.setGroupVisible(0, true);
            menu.setGroupVisible(1, true);
        }
        return super.onPrepareOptionsMenu(menu);
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

    public void getPostTab () {
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(1, true);
            }
        },1000);
    }

    public void groupMembers () {
        FragmentUtils.getGroupMembersFragment()
                .show(getFragmentManager(), "GroupMembersFragment");
    }

    public void deleteGroup () {
        FragmentUtils.getDeleteGroupDialogFragment()
                .show(getFragmentManager(), "DeleteGroupDialogFragment");
    }

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
                    mProcessUser = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
