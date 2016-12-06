package com.example.gabekeyner.nostalgia;

public class Group {

    public String user;
    public String groupName;

    public Group() {
    }


    public Group(String user, String groupName) {
        this.user = user;
        this.groupName = groupName;
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

}
