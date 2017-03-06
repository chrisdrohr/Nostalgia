package com.example.rohrlabs.nostalgia.Firebase;

import com.example.rohrlabs.nostalgia.Adapters.GroupsAdapter;
import com.example.rohrlabs.nostalgia.DialogFragments.GroupFragment;
import com.example.rohrlabs.nostalgia.ObjectClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {

    public static String mGroupKey;
    public static String mUid;
    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getChatRef() {
        if (mGroupKey != null) {
            return getGroupKeyRef().child("chat");
        }
        return null;
    }

    public static DatabaseReference getGroupKeyRef() {
        mGroupKey = GroupFragment.mGroupKey;
        mGroupKey = GroupsAdapter.mGroupKey;
        if (mGroupKey != null) {
            return FirebaseDatabase.getInstance().getReference().child("groups").child(mGroupKey);
        }
        return FirebaseUtil.getGroupRef();
    }

//    public static DatabaseReference getGroupKeyRef() {
//        mGroupKey = GroupFragment.mGroupKey;
//        mGroupKey = GroupsAdapter.mGroupKey;
//        return FirebaseDatabase.getInstance().getReference().child("groups").child(mGroupKey);
//    }

    public static User getUser () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return new User(user.getDisplayName(),user.getPhotoUrl().toString(), null, null, null);
    }
    public static String getUserName () {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser.getDisplayName();
    }
    public static DatabaseReference getLikesRef() {
        return getGroupKeyRef().child("likes");
    }

    public static DatabaseReference getDeletePostRef() {
        return getPostRef();
    }

    public static DatabaseReference getPostRef () {
        return getGroupKeyRef().child("posts");
    }

    public static String getUid () {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static DatabaseReference getCommentsRef () {
        return getGroupKeyRef().child("posts/comments");
    }
    public static DatabaseReference getUserRef () {
        return getBaseRef().child("users/data");
    }

    public static DatabaseReference getUserExistsRef() {
        mUid = getUid();
        return getBaseRef().child("users/exists").child(mUid);
    }
    public static DatabaseReference getGroupRef () {
        return getBaseRef().child("groups");
    }

    public static DatabaseReference getMemberGroupRef () {
        mUid = getUid();
        return getBaseRef().child("members").child(mUid).child("groups");
    }

    public static DatabaseReference getGroupMemberRef () {
        mGroupKey = GroupFragment.mGroupKey;
        mGroupKey = GroupsAdapter.mGroupKey;
        mUid = FirebaseUtil.getUid();
        if (mGroupKey != null) {
            return getGroupRef().child(mGroupKey).child("members").child(mUid);
        }
        return null;
    }

    public static DatabaseReference getMemberProfile() {
        mUid = FirebaseUtil.getUid();
        return getBaseRef().child("members").child(mUid);
    }

    public static DatabaseReference getAddUserRef() {
        return getBaseRef().child("members");
    }
}
