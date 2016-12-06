package com.example.gabekeyner.nostalgia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.gabekeyner.nostalgia.MainActivity.ANONYMOUS;
import static com.example.gabekeyner.nostalgia.R.id.addedUserCircleImageView;
import static com.example.gabekeyner.nostalgia.R.menu.main;

public class GroupsActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView userImageView;
        public AutoTypeTextView userAutoTypeTextView;

        public MessageViewHolder(View v) {
            super(v);
            userAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.addedUserAutoText);
            userImageView = (CircleImageView) itemView.findViewById(addedUserCircleImageView);

        }
    }

    public RecyclerView recyclerView, userRecyclerView;
    public AutoTypeTextView autoTypeTextViewGroupName;
    private StaggeredGridLayoutManager layoutManager;
//    private LinearLayoutManager layoutManager;
    private FirebaseRecyclerAdapter<User, MessageViewHolder>mFirebaseAdapter;

    private DatabaseReference userRef;
    private ValueEventListener valueEventListener;
    private DatabaseReference mChildRef, mAddedUserRef, groupNamePath;
    private StorageReference mStorage;
    private PostAdapter mPostAdapter;
    private GroupAdapter mGroupAdapter;
    private UserAdapter mUserAdapter;
    private GoogleApiClient mGoogleApiClient;
    private Toolbar toolbar;

    private ImageView imageTransition, userImageView;
    private TextView userTextView;
    private EditText mEditText;
    private TextView textView;
    private CircleImageView circleUserImageView;
    private String mUsername, mPhotoUrl, mUid, groupName;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGroup);
        setSupportActionBar(toolbar);

        initViews();
        groupNameDialog();

        autoTypeTextViewGroupName = (AutoTypeTextView) findViewById(R.id.autoTypeTextViewGroupName);

        //INITIALIZE USER RECYCLERvIEW
        userRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAddedFriends);
//        layoutManager = new LinearLayoutManager(this);
//        layoutManager.setStackFromEnd(true);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        userRecyclerView.setItemViewCacheSize(20);
        userRecyclerView.setLayoutManager(layoutManager);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<User, MessageViewHolder>(
                User.class,
                R.layout.added_users,
                MessageViewHolder.class,
                FirebaseUtil.getGroupUserRef()) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, User model, final int position) {
//                viewHolder.userAutoTypeTextView.setText(model.getUserName());
                viewHolder.userAutoTypeTextView.setTextAutoTyping(mUsername);
                viewHolder.userAutoTypeTextView.setTypingSpeed(50);

                if (model.getProfilePicture() == null) {
                    viewHolder.userImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(GroupsActivity.this,
                                            R.drawable.ic_account_circle_black_36dp));
                } else {
                    Glide.with(GroupsActivity.this)
                            .load(mPhotoUrl)
                            .centerCrop()
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.NORMAL)
                            .into(viewHolder.userImageView);
                }


            }
        };
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setAdapter(mFirebaseAdapter);
        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());

    }
    //VIEWS
    private void initViews() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGroups);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mChildRef = FirebaseUtil.getUserRef();
        mGroupAdapter = new GroupAdapter(User.class, R.layout.group_card_view, Viewholder.class, mChildRef, getApplicationContext());
        recyclerView.setAdapter(mGroupAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void groupNameDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = View.inflate(this, R.layout.fragment_group, null);
        builder.setView(view);
        circleUserImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
        textView = (TextView) view.findViewById(R.id.dialogTextView);
        groupNamePath = FirebaseUtil.getGroupRef();
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        mUsername = FirebaseUtil.getUser().getUserName();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Glide.with(this)
                .load(mPhotoUrl)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(circleUserImageView);
        textView.setText(mUsername);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEditText = (EditText) view.findViewById(R.id.groupEditText);
                String groupName = mEditText.getText().toString();
                autoTypeTextViewGroupName.setTextAutoTyping(groupName);
                autoTypeTextViewGroupName.setTypingSpeed(50);
                Toast.makeText(getApplicationContext(), groupName, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
