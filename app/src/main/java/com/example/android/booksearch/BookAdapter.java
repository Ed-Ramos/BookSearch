package com.example.android.booksearch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);

        }

        // Get the {@link Book} object located at this position in the list
        Book currentBook = getItem(position);

        String bookTitle = currentBook.getTitle();
        String bookAuthor = currentBook.getAuthor();

        // Find the TextView in the book_list_item_item.xml layout with the ID book_title_text_view
        TextView BookTitleTextView = (TextView) listItemView.findViewById(R.id.book_title_text_view);

        // Find the TextView in the book_list_item.xml layout with the ID book_author_text_view
        TextView BookAuthorTextView = (TextView) listItemView.findViewById(R.id.book_author_text_view);

        BookTitleTextView.setText(bookTitle);
        BookAuthorTextView.setText("By: " + bookAuthor);

        return listItemView;
    }

}
