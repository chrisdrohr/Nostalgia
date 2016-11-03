package com.example.gabekeyner.nostalgia;

public class Comment {

        private String text;
        private String user;
        private String photoUrl;
        private String timestamp;

    public Comment(String text, String user, String photoUrl, String timestamp) {
        this.text = text;
        this.user = user;
        this.photoUrl = photoUrl;
        this.timestamp = timestamp;
    }

    public Comment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
