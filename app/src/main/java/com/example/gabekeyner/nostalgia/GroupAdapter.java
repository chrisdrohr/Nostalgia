//package com.example.gabekeyner.nostalgia;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.Query;
//
//public class GroupAdapter extends FirebaseRecyclerAdapter<User, Viewholder> {
//
//    private String mPhotoUrl, mUsername, mUid;
//
//    private static final String TAG = GroupAdapter.class.getSimpleName();
//    private Context context;
//    int previousPosition = 0;
//    private String title, imageURL;
//    public ImageView detailImage;
//    private ImageView imageTransition, viewerImageView;
//
//    public GroupAdapter(Class<User> modelClass, int modelLayout, Class<Viewholder> viewHolderClass, Query ref, Context context) {
//        super(modelClass, modelLayout, viewHolderClass, ref);
//        this.context = context;
//    }
//
//    @Override
//    public void populateViewHolder(final Viewholder viewHolder, final User model, int position) {
//        final String user_key = getRef(position).getKey();
//        viewHolder.groupsUsername.setText(model.getUserName());
//        Glide.with(context)
//                .load(model.getProfilePicture())
//                .thumbnail(0.5f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(viewHolder.profilePicture);
//
//        //FOR ANIMATION
////        if (position > previousPosition) {
////            //We are scrolling down
////            AnimationUtil.animate(viewHolder, true);
////        } else { //We are scrolling up
////            AnimationUtil.animate(viewHolder, false);
////        }
////        previousPosition = position;
////        int lastPosition = -1;
//
////        AnimationUtil.setScaleAnimation(viewHolder.mCardView);
////        AnimationUtil.setFadeAnimation(viewHolder.mTitle);
////        AnimationUtil.setAnimation(viewHolder.mCardView, lastPosition);
//        mUsername = FirebaseUtil.getUserName();
//        mPhotoUrl = FirebaseUtil.getUser().getProfilePicture();
//        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
////        Intent intent = ((GroupsActivity) context).getIntent();
////        final String groupKey = intent.getExtras().toString();
//        viewHolder.profilePicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "adapter", Toast.LENGTH_SHORT).show();
////                User user = new User(
////                        mUsername,
////                        mPhotoUrl,
////                        mUid,
////                        null,
////                        null);
////                FirebaseUtil.getGroupUserRef().push().setValue(user);
//            }
//        });
//    }
//}
