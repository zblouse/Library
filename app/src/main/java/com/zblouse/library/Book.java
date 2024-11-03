package com.zblouse.library;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO for the Book object
 */
public class Book implements Serializable {

    public static final String DATABASE_TITLE_KEY = "title";
    public static final String DATABASE_AUTHOR_KEY = "author";
    public static final String DATABASE_READ_KEY = "read";
    public static final String DATABASE_UID_KEY = "uid";
    private String title;
    private String author;
    private String uid;
    private boolean read;

    public Book(String title, String author, String uid, boolean read){
        this.title = title;
        this.author = author;
        this.uid = uid;
        this.read = read;
    }

    public Book(Map<String, Object> data){
        this.title = (String)data.get(DATABASE_TITLE_KEY);
        this.author= (String)data.get(DATABASE_AUTHOR_KEY);
        this.read = (Boolean)data.get(DATABASE_READ_KEY);
        this.uid = (String)data.get(DATABASE_UID_KEY);
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public boolean hasRead(){
        return this.read;
    }

    public void setRead(boolean read){
        this.read = read;
    }

    /**
     * Returns a map object in the format needed to store in firestore
     * @return
     */
    public Map<String, Object> getMap(){
        Map<String, Object> book = new HashMap<>();
        book.put(DATABASE_TITLE_KEY,title);
        book.put(DATABASE_AUTHOR_KEY, author);
        book.put(DATABASE_READ_KEY, read);
        book.put(DATABASE_UID_KEY, uid);
        return book;
    }

    public String getBookDatabaseKey(){
        return uid+title+author;
    }

}
