package edu.byui.cs246.bookwarm.test;

import android.test.AndroidTestCase;

import edu.byui.cs246.bookwarm.GoogleBooksMessenger;

/**
 * Created by Jake on 3/24/2015.
 * Tests connectivity with Google Books
 */
public class testGoogleBooksMessenger extends AndroidTestCase {
    /**
     * I just want to see exactly what the server is sending back, if anything.
     */
    public void seeServerResponseString() {
        //set up the messenger
        GoogleBooksMessenger messenger = new GoogleBooksMessenger();
        //run the search
        messenger.searchGoogleBooks("Harry Potter");

        //output the result
        System.out.println("SERVER RESPONSE...");
        System.out.println(messenger.getServerResponse());
    }
}
