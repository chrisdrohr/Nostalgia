package com.example.gabekeyner.nostalgia;

import android.net.Uri;

import com.google.firebase.storage.StorageMetadata;

public class Post {

    public String imageURL;
    public String title;
    public String user;
    public String timestamp;
    public String uid;



    public Post() {
    }



    public Post(String imageURL, String title, String user, String timestamp, String uid) {
        this.imageURL = imageURL;
        this.title = title;
        this.user = user;
        this.timestamp = timestamp;
        this.uid = uid;

    }

    public Post(Uri url, StorageMetadata metadata) {

    }

//    public Post(String s, String s1, String s3, String timestamp) {
//        this.imageURL = s;
//        this.title = s1;
//        this.user = user;
//        this.timestamp = timestamp;
//
//    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

