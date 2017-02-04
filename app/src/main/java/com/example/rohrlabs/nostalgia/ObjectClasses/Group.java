package com.example.rohrlabs.nostalgia.ObjectClasses;

public class Group {

    public String groupCreator;
    public String groupName;
    public String groupPhoto;
    public String groupId;

    public Group() {
    }


    public Group(String groupCreator, String groupName, String groupPhoto, String groupId) {
        this.groupCreator = groupCreator;
        this.groupName = groupName;
        this.groupPhoto = groupPhoto;
        this.groupId = groupId;

    }

    public String getGroupCreator() {
        return groupCreator;
    }

    public void setGroupCreator(String groupCreator) {
        this.groupCreator = groupCreator;
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
