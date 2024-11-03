package com.zblouse.library;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Firebase Firestore handler for book objects
 */
public class BookDatabaseHandler {

    private static final String bookCollection = "books";

    /**
     * Stores books into firestore
     * @param book
     */
    public static void addBookToDatabase(Book book){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(bookCollection)
                .document(book.getBookDatabaseKey())
                .set(book.getMap());
    }

    /**
     * Retrieves books from firestore, though since firestore is asynchronous, it requries a callback
     * @param uid
     * @param callback
     */
    public static void getBooksForUser(String uid, FirestoreCallback callback){
        System.out.println("Getting books for User: " + uid);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Set<QueryDocumentSnapshot> snapshots = new HashSet<>();
        List<Book> books = new ArrayList<>();
        db.collection(bookCollection)
                .whereEqualTo(Book.DATABASE_UID_KEY,uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            System.out.println("DB query was successful");
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Book book = new Book(document.getData());
                                books.add(book);
                            }
                            callback.dataReturned(books);
                        } else {
                            System.out.println("DB query was not successful");
                        }
                    }
                });

    }
}
