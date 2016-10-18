package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

public class SplashScreen extends AppCompatActivity {

    VideoView videoView;
    ImageView imageView;
    Animation splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

   imageView = (ImageView) findViewById(R.id.splashImageView);
        splash = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash);
        imageView.setAnimation(splash);

//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        VideoView videoHolder = new VideoView(this);
////if you want the controls to appear
//        videoHolder.setMediaController(new MediaController(this));
//        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
//                + R.raw.t); //do not add any extension
////if your file is named sherif.mp4 and placed in /raw
////use R.raw.sherif
//        videoHolder.setVideoURI(video);
//        setContentView(videoHolder);
//        videoHolder.start();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 200);

    }

}
