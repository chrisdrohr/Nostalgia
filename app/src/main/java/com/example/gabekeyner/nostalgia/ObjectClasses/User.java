package com.example.gabekeyner.nostalgia.ObjectClasses;

import java.util.Map;

public class User {
    private String userName;
    private String profilePicture;
    private String uid;
    private String groupId;
    private Map<String, Object> groups;

    public User() {
    }


    public User(String userName, String profilePicture, String uid, String groupId, Map<String, Object> groups) {
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.uid = uid;
        this.groupId = groupId;
        this.groups = groups;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getUid() {
        return uid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Map<String, Object> getGroups() {
        return groups;
    }


}
