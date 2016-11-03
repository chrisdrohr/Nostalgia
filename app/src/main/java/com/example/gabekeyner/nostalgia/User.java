package com.example.gabekeyner.nostalgia;

import java.util.Map;

public class User {
    private String userName;
    private String profilePicture;
    private Map<String, Boolean> posts;
    private Map<String, Object> groups;

    public User() {
    }

    public User(String userName, String profilePicture, Map<String, Boolean> posts, Map<String, Object> groups) {
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.posts = posts;
        this.groups = groups;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public Map<String, Boolean> getPosts() {
        return posts;
    }

    public Map<String, Object> getGroups() {
        return groups;
    }
}
