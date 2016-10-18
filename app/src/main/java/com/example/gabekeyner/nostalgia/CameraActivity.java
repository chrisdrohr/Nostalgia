package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
import com.facebook.FacebookSdk;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CameraActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mTitle;
    ImageView imageView;
    private Button postBtn;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_camera);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        mTitle = (EditText)findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);
        postBtn = (Button)findViewById(R.id.post_button);
        postBtn.setOnClickListener(this);



//
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            // already signed in
//        } else {
//            // not signed in
//        }
//
//        //Builds the sign with Smart Lock feature to remember user credentials
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setIsSmartLockEnabled(false)
//                        .setProviders(
//                                AuthUI.FACEBOOK_PROVIDER)
////                        .setTheme(R.style.) will use for color customization
//                        .build(),
//                RC_SIGN_IN);

        ImageView imageView = (ImageView) findViewById(R.id.cameraImageView);

        Intent intent = getIntent();
        Uri imageUri = intent.getData();
        Picasso.with(this).load(imageUri).into(imageView);
    }
    private void postInformation(){
//        String imageURL = imageView.getContext().toString();
        String title = mTitle.getText().toString().trim();

        Post post = new Post(null, title);
        databaseReference.child(post.getTitle().toString()).setValue(post);
//        databaseReference.child(post.getImageURL()).setValue(post);

        Toast.makeText(this, "Upload Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        postInformation();
    }
}







