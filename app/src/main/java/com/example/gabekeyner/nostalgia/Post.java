package com.example.gabekeyner.nostalgia;

import ihsan.bal.library.base.BeeModel;

public class Post extends BeeModel {

    public String imageURL;
    public String title;
    public String user;
    public String timestamp;

    public Post() {
    }

    public Post(String tag) {
        super(tag);
    }

//    public Post(String tagPref) {
//        super(tagPref);
//    }

    public Post(String referencesname, String imageURL, String title, String user, String timestamp) {
        super(referencesname);
        this.imageURL = imageURL;
        this.title = title;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Post(String imageURL, String title, String user, String timestamp) {
        this.imageURL = imageURL;
        this.title = title;
        this.user = user;
        this.timestamp = timestamp;
    }

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

}
