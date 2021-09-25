package com.main.home_guard_droid;

import static android.content.ContentValues.TAG;

import android.util.Log;
import com.google.firebase.database.*;

public class DatabaseConnector {

    private DatabaseReference mDatabase; // should be final, probably

    public DatabaseConnector()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            //Post post = dataSnapshot.getValue(Post.class);
            // ..
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };
    //mPostReference.addValueEventListener(postListener);
}