package com.zblouse.library;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Firestore Database handler for the User objects
 */
public class UserDatabaseHandler {

    public static void createUser(User user){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(user.getUserId())
                .set(user.getMap());
    }

    /**
     * Asynchronously fetches user objects out of firestore, requires a callback
     * @param userId
     * @param callback
     */
    public static void getUser(String userId, FirestoreCallback callback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        User user = new User(document.getData());
                        callback.userReturned(user);
                    } else {
                        callback.userReturned(null);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    callback.userReturned(null);
                }
            }
        });
    }
}
