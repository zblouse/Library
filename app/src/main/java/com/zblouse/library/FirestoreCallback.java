package com.zblouse.library;

import java.util.List;

public interface FirestoreCallback {
    void dataReturned(List<Book> books);
}
