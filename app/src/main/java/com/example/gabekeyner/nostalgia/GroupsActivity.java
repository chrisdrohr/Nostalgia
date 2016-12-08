package com.example.gabekeyner.nostalgia;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.gabekeyner.nostalgia.FirebaseUtil.getGroupRef;
import static com.example.gabekeyner.nostalgia.FirebaseUtil.getUserRef;
import static com.example.gabekeyner.nostalgia.R.id.addedUserCircleImageView;
import static com.example.gabekeyner.nostalgia.R.menu.main;

public class GroupsActivity extends AppCompatActivity{

    public static class AddedUserViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView userImageView;
        public AutoTypeTextView userAutoTypeTextView;

        public AddedUserViewHolder(View v) {
            super(v);
            userAutoTypeTextView = (AutoTypeTextView) itemView.findViewById(R.id.addedUserAutoText);
            userImageView = (CircleImageView) itemView.findViewById(addedUserCircleImageView);
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImageView;
        public TextView profileTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            profileImageView = (ImageView) itemView.findViewById(R.id.imageViewGroup);
            profileTextView = (TextView) itemView.findViewById(R.id.textViewGroup);
        }
    }
    public RecyclerView recyclerView, userRecyclerView;
    public AutoTypeTextView autoTypeTextViewGroupName;
    private StaggeredGridLayoutManager layoutManager, userLayoutManager;
    private FirebaseRecyclerAdapter<User, AddedUserViewHolder> mFirebaseAdapter;
    private FirebaseRecyclerAdapter<User, UserViewHolder> mUserFirebaseAdapter;
    private String mUsername, mPhotoUrl, mUid, userKey;
    private String groupName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGroup);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        groupName = bundle.getString("groupName");
        autoTypeTextViewGroupName = (AutoTypeTextView) findViewById(R.id.autoTypeTextViewGroupName);
        autoTypeTextViewGroupName.setTextAutoTyping(groupName);
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUsername = FirebaseUtil.getUserName();
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();

        //INITIALIZE USER RECYCLERvIEW
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGroups);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setItemViewCacheSize(30);
        userRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewAddedFriends);
        userLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        userRecyclerView.setItemViewCacheSize(20);
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setAdapter(mFirebaseAdapter);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<User, AddedUserViewHolder>(
                User.class,
                R.layout.added_users,
                AddedUserViewHolder.class,
                getGroupRef().child(groupName)) {

            @Override
            protected void populateViewHolder(final AddedUserViewHolder viewHolder, final User model, final int position) {
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

                ViewAnimator.animate(viewHolder.userImageView)
                        .bounceIn()
                        .duration(500)
                        .start();

                viewHolder.userImageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        userKey = getGroupRef().child(groupName).getKey();
                        return false;
                    }
                });
            }
        };

        mUserFirebaseAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(
                User.class,
                R.layout.group_card_view,
                UserViewHolder.class,
                getUserRef()) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
                viewHolder.profileTextView.setText(model.getUserName());
                Glide.with(GroupsActivity.this)
                        .load(model.getProfilePicture())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.profileImageView);

                viewHolder.profileImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createGroup();
                    }
                });
            }
        };
        recyclerView.setLayoutManager(userLayoutManager);
        recyclerView.setAdapter(mUserFirebaseAdapter);
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setAdapter(mFirebaseAdapter);
        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //LAYOUTS & ORIENTATIONS
//        switch (id) {
//            case R.id.fresh_config_menu:
//                fetchConfig();
//                return true;
//            case R.id.sign_out_menu:
//                mFirebaseAuth.signOut();
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                mUsername = ANONYMOUS;
//                startActivity(new Intent(this, SignInActivity.class));
//                return true;
//            case R.id.linearViewVertical:
//                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
//                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
//                break;
//            case R.id.twoViewVertical:
//                StaggeredGridLayoutManager mStaggered2VerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                mStaggered2VerticalLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//                recyclerView.setLayoutManager(mStaggered2VerticalLayoutManager);
//                break;
//            case R.id.staggeredViewVertical:
//                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//                mStaggeredVerticalLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//                recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
//                break;
//            default:
//        }
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

    public void createGroup() {
            User user = new User(
                    mUsername,
                    mPhotoUrl,
                    mUid,
                    null,
                    null);
            getGroupRef().child(groupName).push().setValue(user);
    }
}
