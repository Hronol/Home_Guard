package com.main.home_guard_droid;

import static android.content.ContentValues.TAG;

import android.util.Log;
import com.google.firebase.database.*;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnector {

    private static final String TAG = "ReadAndWriteSnippets";

    private DatabaseReference mDatabase;

    public ReadAndWriteSnippets(DatabaseReference database) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void addPostEventListener(DatabaseReference mPostReference) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Post post = dataSnapshot.getValue(Post.class);
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }
}