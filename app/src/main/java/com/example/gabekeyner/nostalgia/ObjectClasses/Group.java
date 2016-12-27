package com.example.gabekeyner.nostalgia.ObjectClasses;

public class Group {

    public String user;
    public String groupName;
    public String groupPhoto;
    public String groupId;

    public Group() {
    }


    public Group(String user, String groupName, String groupPhoto, String groupId) {
        this.user = user;
        this.groupName = groupName;
        this.groupPhoto = groupPhoto;
        this.groupId = groupId;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
