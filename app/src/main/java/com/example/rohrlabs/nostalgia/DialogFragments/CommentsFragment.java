//package com.example.gabekeyner.nostalgia.DialogFragments;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.widget.CardView;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Priority;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.dragankrstic.autotypetextview.AutoTypeTextView;
//import com.example.gabekeyner.nostalgia.Activities.DetailActivity;
//import com.example.gabekeyner.nostalgia.AnimationUtil;
//import com.example.gabekeyner.nostalgia.Firebase.FirebaseUtil;
//import com.example.gabekeyner.nostalgia.ObjectClasses.Comment;
//import com.example.gabekeyner.nostalgia.R;
//import com.github.florent37.viewanimator.ViewAnimator;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class CommentsFragment extends DialogFragment{
//
//    private CircleImageView userImageView;
//    private String mPhotoUrl, mUsername, mUid, commentPath, mCommentKey;
//    private TextView textView;
//    private CardView cardView;
//    private ImageView commentBg;
//    private DatabaseReference databaseReference;
//
//    private AutoTypeTextView autoTypeTextView;
//    private EditText mEditText;
//    public static final String mPost_key = null;
//    public static String post_image;
//    private FloatingActionButton mSendFab;
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        final LayoutInflater inflater = getActivity().getLayoutInflater();
//        final View view = inflater.inflate(R.layout.fragment_comments, null);
//
//        userImageView = (CircleImageView) view.findViewById(R.id.dialogUserImageView);
//        textView = (TextView) view.findViewById(R.id.dialogTextView);
//        cardView = (CardView) view.findViewById(R.id.cardViewprofile);
//        mEditText = (EditText) view.findViewById(R.id.commentEditText);
//        commentBg = (ImageView) view.findViewById(R.id.comment_bg);
//
//        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
//        mUsername = FirebaseUtil.getUser().getUserName();
//        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        post_image = DetailActivity.post_image;
//
//        Bundle bundle = getArguments();
//        final String mPostKey = bundle.getString("postKey","");
//
//        commentPath = FirebaseUtil.getBaseRef().child("posts").getKey();
//
//        // Send function to comment
//        SimpleDateFormat time = new SimpleDateFormat("MM/dd-hh:mm");
//        final String mCurrentTimestamp = time.format(new Date());
//        mSendFab = (FloatingActionButton) view.findViewById(R.id.sendFab);
//        AnimationUtil.setFadeAnimation(mSendFab);
//        mSendFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                databaseReference = FirebaseUtil.getCommentsRef().child(mPostKey);
//                DatabaseReference ref = databaseReference.push();
//                mCommentKey = ref.getKey();
//                Comment comment = new
//                        Comment(mEditText.getText().toString(),
//                        mUsername,
//                        mPhotoUrl,
//                        mPostKey,
//                        mCurrentTimestamp,
//                        mUid,
//                        mCommentKey
//                );
//                ref.setValue(comment);
////                FirebaseUtil.getCommentsRef().child(mPostKey)
////                        .push().setValue(comment);
//                mEditText.setText("");
//            }
//        });
//
//        mEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().trim().length() > 0) {
//                    mSendFab.setEnabled(true);
//                } else {
//                    mSendFab.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        Glide.with(CommentsFragment.this)
//                .load(post_image)
//                .priority(Priority.IMMEDIATE)
//                .thumbnail(0.1f)
//                .crossFade()
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(commentBg);
//
//        Glide.with(CommentsFragment.this)
//                .load(mPhotoUrl)
//                .priority(Priority.IMMEDIATE)
////                .thumbnail(0.1f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(userImageView);
//        textView.setText(mUsername);
//
//        ViewAnimator.animate(cardView)
//                .zoomIn()
//                .duration(100)
//                .andAnimate(userImageView, textView, mEditText)
//                .alpha(0,0)
//                .thenAnimate(userImageView)
//                .alpha(0,1)
//                .rollIn()
//                .duration(100)
//                .thenAnimate(textView)
//                .slideBottom()
//                .duration(100)
//                .thenAnimate(mEditText)
//                .slideBottom()
//                .descelerate()
//                .duration(100)
//                .start();
//
//        builder.setView(view);
//        builder.setPositiveButton("Post",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        mSendFab.callOnClick();
//                        ((DetailActivity)getActivity()).postComment();
//                    }
//                }
//        )
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
////                                (()getActivity()).doNegativeClick();
//                            }
//                        }
//                );
//        return builder.create();
//    }
//}
