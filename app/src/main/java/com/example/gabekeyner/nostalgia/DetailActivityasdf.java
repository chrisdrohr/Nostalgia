//package com.example.gabekeyner.nostalgia;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.support.constraint.ConstraintLayout;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.view.Window;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Priority;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.dragankrstic.autotypetextview.AutoTypeTextView;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.github.florent37.viewanimator.ViewAnimator;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;
//import com.sloop.fonts.FontsManager;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static com.example.gabekeyner.nostalgia.R.anim;
//import static com.example.gabekeyner.nostalgia.R.color;
//import static com.example.gabekeyner.nostalgia.R.drawable;
//import static com.example.gabekeyner.nostalgia.R.id;
//import static com.example.gabekeyner.nostalgia.R.layout;
//
//public class DetailActivity extends AppCompatActivity {
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//
//        super.onCreate(savedInstanceState);
//        setContentView(layout.detail_view);
//
//        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//    }
//
//    void showDeleteDialog() {
//        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
//        deleteDialogFragment.show(fragmentManager, "Delete Dialog Fragment");
//    }
//
//    void showCommentDialog() {
//        CommentsFragment commentsFragment = new CommentsFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("postKey", getIntent().getExtras().getString("post_key"));
//        commentsFragment.setArguments(bundle);
//        commentsFragment.show(fragmentManager, "Comments Fragment");
//    }
//
//    public void postComment() {
//
//        snackbar = Snackbar.make(relativeLayout, "Comment Posted", 1000);
//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),color.DarkColor));
//        snackbar.show();
//        ViewAnimator.animate(mCommentFab, mSendFab)
//                .tada()
//                .duration(1000)
//                .andAnimate(mCommentFab, mSendFab)
//                .translationY(0,-200)
//                .duration(200)
//                .thenAnimate(mCommentFab)
//                .scale(1,0)
//                .duration(100)
//                .thenAnimate(mSendFab)
//                .startDelay(300)
//                .translationX(0,500)
//                .descelerate()
//                .duration(600)
//                .thenAnimate(mCommentFab, mSendFab)
//                .translationY(-200, 0)
//                .duration(800)
//                .thenAnimate(mCommentFab)
//                .bounceIn()
//                .duration(650)
//                .start();
//}
//
//    public void doPositiveClick() {
//                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
//                startActivity(intent);
//        FirebaseUtil.getDeletePostRef().child(mPostKey).removeValue();
//        finish();
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        finishAfterTransition();
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finishAfterTransition();
//    }
//}