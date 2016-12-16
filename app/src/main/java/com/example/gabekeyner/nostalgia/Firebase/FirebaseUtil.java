package com.example.gabekeyner.nostalgia.Firebase;

import com.example.gabekeyner.nostalgia.ObjectClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {

    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static User getUser () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return new User(user.getDisplayName(),user.getPhotoUrl().toString(), null, null, null);
    }
    public static String getUserName () {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser.getDisplayName();
    }
    public static DatabaseReference getLikesRef() {
        return getBaseRef().child("likes");
    }

    public static DatabaseReference getDeletePostRef() {
        return FirebaseDatabase.getInstance().getReference().child("posts");
    }
    public static DatabaseReference getPostRef() {
        return FirebaseDatabase.getInstance().getReference().child("posts");
    }

    public static String getUid () {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static DatabaseReference getCommentsRef () {
        return getBaseRef().child("posts/comments");
    }
    public static DatabaseReference getUserRef () {
        return getBaseRef().child("users/data");
    }

    public static DatabaseReference getUserExistsRef() {
        return getBaseRef().child("users/exists");
    }
    public static DatabaseReference getGroupRef () {
        return getBaseRef().child("groups");
    }

    public static DatabaseReference getGroupMemberRef () {
        return getBaseRef().child("groups/members");
    }
}
