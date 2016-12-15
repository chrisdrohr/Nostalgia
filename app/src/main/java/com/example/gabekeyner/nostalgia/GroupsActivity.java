package com.example.gabekeyner.nostalgia;

import android.content.Intent;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.gabekeyner.nostalgia.FirebaseUtil.getGroupMemberRef;
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

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        public ImageView groupImageView;
        public TextView groupTextView;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupImageView = (ImageView) itemView.findViewById(R.id.imageViewGroup);
            groupTextView = (TextView) itemView.findViewById(R.id.textViewGroup);
        }
    }
    public RecyclerView recyclerView, userRecyclerView, groupRecyclerView;
    public AutoTypeTextView autoTypeTextViewGroupName;
    private StaggeredGridLayoutManager layoutManager, userLayoutManager, groupLayoutManager;
    private FirebaseRecyclerAdapter<User, AddedUserViewHolder> mFirebaseAdapter;
    private FirebaseRecyclerAdapter<User, UserViewHolder> mUserFirebaseAdapter;
    private FirebaseRecyclerAdapter<Group, GroupViewHolder> mGroupFirebaseAdapter;
    private String mUsername, mPhotoUrl, mUid, userKey;
    private String groupName, groupKey;
    private DatabaseReference databaseReference;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        toolbar = (Toolbar) findViewById(R.id.toolbarGroup);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        groupName = bundle.getString("groupName");
        groupKey = bundle.getString("groupKey");

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
        groupLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        userRecyclerView.setItemViewCacheSize(20);
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setAdapter(mFirebaseAdapter);

//        createGroup();
//        addUser();

//        getGroupRef().addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                Bundle bundle = getIntent().getExtras();
////                groupName = bundle.getString("groupName");
////                groupKey = bundle.getString("groupKey");
//                Toast.makeText(GroupsActivity.this, groupKey, Toast.LENGTH_SHORT).show();
//                mFirebaseAdapter = new FirebaseRecyclerAdapter<User, AddedUserViewHolder>(
//                        User.class,
//                        R.layout.added_users,
//                        AddedUserViewHolder.class,
//                        getGroupMemberRef().child(groupKey)) {
//
//                    @Override
//                    protected void populateViewHolder(final AddedUserViewHolder viewHolder, final User model, final int position) {
//
//                        viewHolder.userAutoTypeTextView.setTextAutoTyping(mUsername);
//                        viewHolder.userAutoTypeTextView.setTypingSpeed(50);
//
//                        if (model.getProfilePicture() == null) {
//                            viewHolder.userImageView
//                                    .setImageDrawable(ContextCompat
//                                            .getDrawable(GroupsActivity.this,
//                                                    R.drawable.ic_account_circle_black_36dp));
//                        } else {
//                            Glide.with(GroupsActivity.this)
//                                    .load(mPhotoUrl)
//                                    .centerCrop()
//                                    .thumbnail(0.5f)
//                                    .crossFade()
//                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .priority(Priority.NORMAL)
//                                    .into(viewHolder.userImageView);
//                        }
//
//                        ViewAnimator.animate(viewHolder.userImageView)
//                                .bounceIn()
//                                .duration(500)
//                                .start();
//
//                        viewHolder.userImageView.setOnLongClickListener(new View.OnLongClickListener() {
//                            @Override
//                            public boolean onLongClick(View v) {
//                                userKey = getGroupRef().child(groupName).getKey();
//                                return false;
//                            }
//                        });
//                    }
//                };
//                        getGroupRef().removeEventListener(this);
//                        getGroupMemberRef().removeEventListener(this);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        mFirebaseAdapter = new FirebaseRecyclerAdapter<User, AddedUserViewHolder>(
                User.class,
                R.layout.added_users,
                AddedUserViewHolder.class,
                getGroupMemberRef().child(groupKey)) {

            @Override
            protected void populateViewHolder(final AddedUserViewHolder viewHolder, final User model, final int position) {

                groupKey = getRef(position).getKey();

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
                        addUser();
                    }
                });
            }
        };
        recyclerView.setLayoutManager(userLayoutManager);
        recyclerView.setAdapter(mUserFirebaseAdapter);
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setAdapter(mFirebaseAdapter);
        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());

        mGroupFirebaseAdapter = new FirebaseRecyclerAdapter<Group, GroupViewHolder>(
                Group.class,
                R.layout.current_group_card_view,
                GroupViewHolder.class,
                getGroupRef()) {
            @Override
            protected void populateViewHolder(GroupViewHolder viewHolder, Group model, int position) {
//                Toast.makeText(GroupsActivity.this, groupKey, Toast.LENGTH_SHORT).show();

                viewHolder.groupTextView.setText(model.getGroupName());
                Glide.with(GroupsActivity.this)
                        .load(model.getGroupPhoto())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.groupImageView);
//                final String groupKey = getRef(position).getKey();
//                getRef(position).push();
//                Intent intent = new Intent(groupKey);
//                intent.putExtra("groupKey", getRef(position).getKey());

            }
        };
        recyclerView.setLayoutManager(groupLayoutManager);
        recyclerView.setAdapter(mGroupFirebaseAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //LAYOUTS & ORIENTATIONS
        switch (id) {
            case R.id.twoViewVertical:
                recyclerView.setLayoutManager(groupLayoutManager);
                recyclerView.setAdapter(mGroupFirebaseAdapter);
//                Toast.makeText(this, getGroupRef().child(groupName).getKey(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.staggeredViewVertical:
               recyclerView.setLayoutManager(userLayoutManager);
                recyclerView.setAdapter(mUserFirebaseAdapter);
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
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewAnimator.animate(toolbar)
                .slideTop()
                .duration(300)
                .andAnimate(recyclerView)
                .slideBottom()
                .duration(300)
                .start();
    }

    public void createGroup() {
        databaseReference = getGroupRef();
        DatabaseReference ref = databaseReference.push();

        final Group group = new Group(
                mUsername,
                groupName,
                mPhotoUrl,
                null);
//                getGroupRef().push().setValue(group);
        ref.setValue(group);
        groupKey = ref.getKey();
        Intent intent = new Intent(getApplicationContext(), GroupsActivity.class);
        intent.putExtra("groupKey", groupKey);
                Toast.makeText(this, groupKey, Toast.LENGTH_SHORT).show();

        addUser();
    }
    public void addUser() {
        Toast.makeText(this, groupKey, Toast.LENGTH_SHORT).show();
        User user = new User(
                mUsername,
                mPhotoUrl,
                mUid,
                null,
                null);
        getGroupMemberRef().child(groupKey).push().setValue(user);

    }
}
