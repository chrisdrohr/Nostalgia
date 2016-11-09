package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dragankrstic.autotypetextview.AutoTypeTextView;

public class ImageViewerActivity extends AppCompatActivity implements OnClickListener {

    private ImageView imageView;
    private AutoTypeTextView autoTypeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        imageView = (ImageView) findViewById(R.id.imageViewer);
        autoTypeTextView = (AutoTypeTextView) findViewById(R.id.autoTypeTextView);

        //Receive Data
        Intent intent = this.getIntent();
        String title = intent.getExtras().getString("title");
        String imageUrl = intent.getExtras().getString("imageURL");

        //Bind Data
        Glide.with(this).load(imageUrl).thumbnail(0.1f).into(imageView);
        autoTypeTextView.setTextAutoTyping(title);

        //Animate text
        imageView.setOnClickListener(this);

    }
    Post model = new Post();

//    public void openDetail(String title, String imageURL) {
//        Intent intent = new Intent(this, DetailActivity.class);
//        intent.putExtra("title", title);
//        intent.putExtra("imageURL", imageURL);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }



    @Override
    public void onClick (View v)  {
    }
}
