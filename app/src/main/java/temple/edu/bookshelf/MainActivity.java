// Tuyen Pham 915591991 CIS3515//

package temple.edu.bookshelf;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {

    ArrayList<Book> booksToDisplay;

    boolean twoPanes;
    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;

    EditText textView;
    Button searchButton;
    int currentBookId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPanes = (findViewById(R.id.detailsFrame) != null);

        booksToDisplay = getBooks();

        if(savedInstanceState != null) {
            booksToDisplay = (ArrayList<Book>)savedInstanceState.getSerializable("key");
            currentBookId = savedInstanceState.getInt("currentBookId");

            //Attempt to display the last-displayed book
            if(currentBookId != 0) {
                bookSelected(currentBookId);
            }
        }

        bookListFragment = BookListFragment.newInstance(booksToDisplay);
        FragmentManager f = getSupportFragmentManager();
        FragmentTransaction t = f.beginTransaction();
        t.add(R.id.frame1, bookListFragment);

        if(twoPanes) {
            bookDetailsFragment = new BookDetailsFragment();
            t.add(R.id.detailsFrame, bookDetailsFragment);
        }

        t.commit();
        f.executePendingTransactions();

        textView = (EditText)findViewById(R.id.searchField);
        searchButton = (Button)findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String searchTerm = textView.getText().toString();
                booksToDisplay.clear();
            //    booksToDisplay = new ArrayList<>();

                ArrayList<Book> allBooks = getBooks();
                for(int i = 0; i < allBooks.size(); i++) {
                    Book currentBook = allBooks.get(i);
                    if(currentBook.getTitle().contains(searchTerm)) {
                        booksToDisplay.add(currentBook);
                    } else if(currentBook.getAuthor().contains(searchTerm)) {
                        booksToDisplay.add(currentBook);
                    }
                }

                FragmentManager f = getSupportFragmentManager();
                FragmentTransaction t = f.beginTransaction();

                t.addToBackStack(null).replace(R.id.frame1, BookListFragment.newInstance(booksToDisplay));
                t.commit();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putSerializable("key", booksToDisplay);
        outState.putInt("currentBookId", currentBookId);

        super.onSaveInstanceState(outState, outPersistentState);
    }

    private ArrayList<Book> getBooks() {
        final ArrayList<Book> arrayList = new ArrayList<>();

        arrayList.add(new Book(1, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", ""));
        arrayList.add(new Book(2, "Eragon", "Christopher Paolini", ""));
        arrayList.add(new Book(3, "A Walk in the Woods", "Bill Bryson", ""));
        arrayList.add(new Book(4, "A Tale of Two Cities", "Charles Dickens", ""));
        arrayList.add(new Book(5, "Ringworld", "Larry Niven", ""));
        arrayList.add(new Book(6, "The Restaurant at the End of the Universe", "Douglas Adams", ""));
        arrayList.add(new Book(7, "Life, the Universe and Everything", "Douglas Adams", ""));
        arrayList.add(new Book(8, "So Long, and Thanks for All the Fish", "Douglas Adams", ""));
        arrayList.add(new Book(9, "American Gods", "Neil Gaiman", ""));
        arrayList.add(new Book(10, "A Wizard Of Earthsea", "Ursula K. Le Guin", ""));

        String url = "https://kamorris.com/lab/abp/booksearch.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(MainActivity.this, "" + response.length(), Toast.LENGTH_LONG).show();
                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject temp = response.getJSONObject(i);
                                Book newBook = new Book(
                                        temp.getInt("id"),
                                        temp.getString("title"),
                                        temp.getString("author"),
                                        temp.getString("coverURL")
                                );
                                arrayList.add(newBook);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
        return arrayList;
    }

    @Override
    public void bookSelected(int index) {
        FragmentManager f = getSupportFragmentManager();
        FragmentTransaction t = f.beginTransaction();
        Book toPass = getBooks().get(index);

        if(twoPanes) {
            bookDetailsFragment.displayBook(toPass);
        } else {
            currentBookId = toPass.getId();
            t.addToBackStack(null).replace(R.id.frame1, BookDetailsFragment.newInstance(
                    toPass.getId(),
                    toPass.getTitle(),
                    toPass.getAuthor(),
                    toPass.getCoverURL()
            ));
        }
        t.commit();
    }
}

