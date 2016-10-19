//package com.example.gabekeyner.nostalgia;
//
//import android.app.Activity;
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.view.animation.ScaleAnimation;
//
//import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class Adapter extends RecyclerView.Adapter<FirebaseViewHolder> {
//
//    private static final String TAG = "Adapter";
//    private final Activity activity;
//    private List<Post> posts = new ArrayList<>();
//    private Context context;
//
//    int previousPosition = 0;
//
//    public Adapter(Activity activity) {
//        this.activity = activity;
//    }
//
//
//    @Override
//    public FirebaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
//        return new FirebaseViewHolder(itemView);
//
//    }
//
//    @Override
//    public void onBindViewHolder(FirebaseViewHolder holder, int position) {
//
//        holder.bindPost(posts.get(position));
//
//    }
//
////    @Override
////    public void onBindViewHolder(final Adapter.ViewHolder holder, final int position) {
////        holder.mTextView.setText(images.get(position).getImageHelper_name());
////
////        Picasso.with(context)
////                .load(images.get(position)
////                        .getImageHelper_url())
////                .resize(800, 500)
////                .centerCrop()
////                .into(holder.mImageView);
////
////        //FOR ANIMATION
////        if(position > previousPosition){
////            //We are scrolling down
////            AnimationUtil.animate(holder, true);
////        }else { //We are scrolling up
////            AnimationUtil.animate(holder, false);
////        }
////        previousPosition = position;
////
////        setScaleAnimation(holder.mImageView);
////        setFadeAnimation(holder.mTextView);
////        setAnimation(holder.mImageView, lastPosition);
////
////        holder.mImageView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(context, "on click position" + position, Toast.LENGTH_SHORT).show();
////            }
////
////        });
////
////        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
////            @Override
////            public boolean onLongClick(View v) {
////                Toast.makeText(context, "on long clickposition" + position, Toast.LENGTH_SHORT).show();
////                return true;
////            }
////        });
////    }
//
//
//    @Override
//    public int getItemCount() {
//        return posts.size();
//    }
//
////    public class ViewHolder extends RecyclerView.ViewHolder {
////        public TextView mTextView;
////        public ImageView mImageView;
////        public ViewHolder(View itemView) {
////            super(itemView);
////            mTextView = (TextView) itemView.findViewById(R.id.textView);
////            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
////        }
////    }
//
//    //ANIMATIONS
//    private final static int SCALE_DURATION = 180;
//    private final static int FADE_DURATION = 2000;
//
//    private void setFadeAnimation(View view) {
//        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(FADE_DURATION);
//        view.startAnimation(anim);
//    }
//
//    private void setScaleAnimation(View view) {
//        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        anim.setDuration(SCALE_DURATION);
//        view.startAnimation(anim);
//    }
//
//    private int lastPosition = -1;
//
//    private void setAnimation(View viewToAnimate, int position) {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
//            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
//            viewToAnimate.startAnimation(anim);
//            lastPosition = position;
//        }
//    }
//
//
//}
