package com.example.gabekeyner.nostalgia;

public class Post {

    public String thumbURL;
    public String imageURL;
    public String title;
    public String user;
    public String timestamp;

    public Post(String thumbURL, String imageURL, String title, String user, String timestamp) {
        this.thumbURL = thumbURL;
        this.imageURL = imageURL;
        this.title = title;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Post() {
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
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
