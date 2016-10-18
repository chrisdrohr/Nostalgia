package com.example.gabekeyner.nostalgia;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabekeyner.nostalgia.DatabaseActivitys.Constants;
import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final int MAX_WIDTH = 800;
    private static final int MAX_HEIGHT = 500;

    View mView;
    Context mContext;

    public FirebaseViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindPost (Post post){
        ImageView imageView = (ImageView) mView.findViewById(R.id.imageView);
        TextView textView = (TextView) mView.findViewById(R.id.textView);

        Picasso.with(mContext)
                .load(post.getImageURL())
                .resize(MAX_WIDTH,MAX_HEIGHT)
                .centerCrop()
                .into(imageView);

    }

    @Override
    public void onClick(View v) {
        final ArrayList<Post> posts = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_POSTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    posts.add(snapshot.getValue(Post.class));
                }
                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("posts", Parcels.wrap(posts));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
