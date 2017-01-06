package com.example.gabekeyner.nostalgia.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
import com.example.gabekeyner.nostalgia.R;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sloop.fonts.FontsManager;

import static com.example.gabekeyner.nostalgia.R.menu.group;

public class GroupsActivity extends AppCompatActivity{

    public RecyclerView recyclerView;
    public AutoTypeTextView autoTypeTextViewGroupName;
    private String mUsername, mPhotoUrl, mUid, groupName, groupPhoto;
    private DatabaseReference databaseReference;
    private Toolbar toolbar;
    private ImageView imageView, groupBg;
    public static Context mContext;
    public static String groupKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        toolbar = (Toolbar) findViewById(R.id.toolbarGroup);
        setSupportActionBar(toolbar);



        Bundle bundle = getIntent().getExtras();
        groupName = bundle.getString("groupName");
        groupKey = bundle.getString("groupKey");
        groupPhoto = bundle.getString("groupPhoto");
//        Toast.makeText(this, groupKey, Toast.LENGTH_SHORT).show();

        imageView = (ImageView) findViewById(R.id.userProfileImageView);
        groupBg = (ImageView) findViewById(R.id.groupBg);
        autoTypeTextViewGroupName = (AutoTypeTextView) findViewById(R.id.autoTypeTextViewGroupName);
        autoTypeTextViewGroupName.setTextAutoTyping(groupName);
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUsername = FirebaseUtil.getUserName();
        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());

        FontsManager.initFormAssets(this, "fonts/Roboto-Regular.ttf");
        FontsManager.changeFonts(autoTypeTextViewGroupName);

        Toast.makeText(GroupsActivity.this, groupPhoto, Toast.LENGTH_SHORT).show();

        Glide.with(GroupsActivity.this)
                .load(groupPhoto)
                .centerCrop()
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.5f)
                .crossFade()
                .into(groupBg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        LAYOUTS & ORIENTATIONS
        switch (id) {
            case R.id.save:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Group Saved!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.discard:
                FirebaseUtil.getGroupRef().child(groupKey).removeValue();
                Intent discardIntent = new Intent(this, MainActivity.class);
                startActivity(discardIntent);
                Toast.makeText(this, "Group Discarded", Toast.LENGTH_SHORT).show();
                break;
            default:
    }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(group, menu);
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
}
