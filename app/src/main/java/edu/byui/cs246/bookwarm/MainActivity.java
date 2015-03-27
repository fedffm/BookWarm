package edu.byui.cs246.bookwarm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    /*--------------------------------------------------------------------------------------------*/
    public Library  library = Library.getInstance(); // Singleton
    public ListView list;
    /*--------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the database
        library.instantiateDatabase(this);

        // Set up the List View
        setupCustomListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.add_new_book) {
            Intent intent = new Intent(MainActivity.this, AddBookMethod.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void test(View view) {
        // Create a book with two notes
        Book book = new Book("Test Book", "Author");
        book.setImageId(R.mipmap.ic_generic_cover);
        book.addNote(new Note("This is a test note"));
        book.addNote(new Note(250, "This is a note with a page number"));

        // Add the book and display a message
        if (!Library.getInstance().contains(book)) {
            Library.getInstance().addBook(book);
            Log.i("DBManager", "Added Book: " + book.toString() + " with an ID of: " + book.getId());
            for (Note note : book.getNotes()) {
                Log.i("DBManager", "Added Note with an ID of: " + note.getId() +
                " corresponding to Book " + note.getBookId());
            }
        } else {
            Log.e("DBManager", "Database already contains book: " + book.getTitle());
        }
        setupCustomListView();
    }

    /**
     * Self-explanatory
     */
    private void setupCustomListView() {
        CustomLibraryList adapter = new CustomLibraryList(MainActivity.this, library);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        //The list listener itself, in all its glory
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create and start the new activity
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);

                //second part is basically just getting the corresponding book from the library,
                //the listView is just a visual representation of what's in the library, after all.
                intent.putExtra("thisBook", library.getBooks().get((int)id));

                //ready, go.
                startActivity(intent);
            }
        });
    }

    /**
     * Clear the books from the library, and re-display the listview
     */
    public void clearLibrary(View view) {
        library.clear();
        setupCustomListView();
    }
}