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


    public static class Comment {

            private String text;
            private String user;
            private String photoUrl;
            private String postKey;
            private String timestamp;

        public Comment(String text, String user, String photoUrl, String postKey, String timestamp) {
            this.text = text;
            this.user = user;
            this.photoUrl = photoUrl;
            this.postKey = postKey;
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
    }
}
