package com.zblouse.library;

import java.util.List;

/**
 * Interface for returning a list of books and the user object from Firestore
 */
public interface FirestoreCallback {
    void dataReturned(List<Book> books);
    void userReturned(User user);
}
