package com.example.android.booksearch;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();


    /**
     * URL for book list from google books site
     */
    //private static final String BOOK_REQUEST_URL =
           // "https://www.googleapis.com/books/v1/volumes?q=cancer%20treatment&maxResults=10&key=AIzaSyDq4yY0HJQIZJqsi3G35Yu3zgGujxmBlSs";

    private static final String  FIXED_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    private static final String  MY_KEY =
            "&maxResults=10&key=AIzaSyDq4yY0HJQIZJqsi3G35Yu3zgGujxmBlSs";

    private String category;

    private String BOOK_REQUEST_URL;


    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * Adapter for the list of books
     */
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView listView = (ListView) findViewById(R.id.list);


        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        listView.setAdapter(mAdapter);


        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);


          // Find the View that shows the search button
        Button bookSearch = (Button) findViewById(R.id.search_button);

        mEmptyStateTextView.setText(R.string.no_books_searched);

        // Set a click listener on that button
        bookSearch.setOnClickListener(new View.OnClickListener() {

            // The code in this method will be executed when the search button is clicked on.
            @Override
            public void onClick(View view) {

                View loadingIndicator = findViewById(R.id.loading_indicator);

                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {

                    loadingIndicator.setVisibility(View.VISIBLE);

                    EditText categoryField = (EditText) findViewById(R.id.category_field);
                    category = categoryField.getText().toString().trim();
                    BOOK_REQUEST_URL = buildURL(category);
                    // restart loader if one is running or start a new one if it is not running
                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, BookActivity.this);

                    } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                   // View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);

                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_internet_connection);

                }

            }

        });

    } //end of onCreate method


    private String buildURL(String inputString) {

        StringBuilder url = new StringBuilder(FIXED_URL);
        return url.append(inputString.replace(" ", "+")).append(MY_KEY).toString();

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "Our current URL is :" + BOOK_REQUEST_URL);
        // Create a new loader for the given URL
        return new BookLoader(this, BOOK_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        Log.v(LOG_TAG, "On load finished Books is :" + books);

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mEmptyStateTextView.setText(R.string.no_books);

        // Clear the adapter of previous book data
        mAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

} //end of class declaration
