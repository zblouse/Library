package com.zblouse.library;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookDatabaseHandler {

    public static void addBookToDatabase(Book book){
        //TODO move this to it's own activity with user entry fields
        private void initializeUserData(){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Map<String, Object> newUser = new HashMap<>();
            newUser.put("name","ZachTest1");
            newUser.put("uid", user.getUid());
            db.collection("users")
                    .add(newUser)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }
}
