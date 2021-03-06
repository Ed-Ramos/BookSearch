package com.example.android.booksearch;

public class Book {

    /** Value that represents title of book*/
    private String mTitle;

    /** Value that represents Author(s) of book*/
    private String mAuthor;

    public Book(String Title, String Author) {
        mTitle = Title;
        mAuthor = Author;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
