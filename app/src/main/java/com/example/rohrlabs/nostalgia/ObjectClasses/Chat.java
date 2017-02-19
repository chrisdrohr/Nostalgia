package com.example.rohrlabs.nostalgia.ObjectClasses;


public class Chat {

    private String text;
    private String user;
    private String photoUrl;
    private String postKey;
    private String timestamp;
    private String uid;
    private String key;

    public Chat(String text, String user, String photoUrl, String postKey, String timestamp, String uid, String key) {
        this.text = text;
        this.user = user;
        this.photoUrl = photoUrl;
        this.postKey = postKey;
        this.timestamp = timestamp;
        this.uid = uid;
        this.key = key;
    }

    public Chat() {
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

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
