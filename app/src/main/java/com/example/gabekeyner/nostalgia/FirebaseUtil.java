package com.example.gabekeyner.nostalgia;

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
        return new User(user.getDisplayName(),user.getPhotoUrl().toString(),null, null);
    }
    public static DatabaseReference getLikesRef() {
        return getBaseRef().child("likes");
    }

    public static String getUid () {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
