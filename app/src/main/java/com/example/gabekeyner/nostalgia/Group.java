package com.example.gabekeyner.nostalgia;

public class Group {

    public String User;
    public String groupName;
    public String groupUser;
    public int numPeople;

    public Group() {
    }

    public Group(String user, String groupName, String groupUser, int numPeople) {
        User = user;
        this.groupName = groupName;
        this.groupUser = groupUser;
        this.numPeople = numPeople;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }
}
