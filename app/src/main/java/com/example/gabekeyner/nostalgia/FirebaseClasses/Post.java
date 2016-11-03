package com.example.gabekeyner.nostalgia.FirebaseClasses;

public class Post {

    public String imageURL;
    public String title;

    public Post() {

    }

    public Post(String imageURL, String title) {
        this.imageURL = imageURL;
        this.title = title;
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
}
