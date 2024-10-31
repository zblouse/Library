package com.zblouse.library;

public class Book {
    private String title;
    private String author;
    private boolean read;

    public Book(String title, String author, boolean read){
        this.title = title;
        this.author = author;
        this.read = read;
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
}
