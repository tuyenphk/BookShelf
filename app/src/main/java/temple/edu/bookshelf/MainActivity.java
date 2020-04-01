// Tuyen Pham 915591991 CIS3515//

package temple.edu.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {

    ArrayList<Book> booksToDisplay;

    boolean twoPanes;
    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;

    EditText textView;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPanes = (findViewById(R.id.detailsFrame) != null);

        booksToDisplay = getBooks();

        if(savedInstanceState != null) {
            booksToDisplay = (ArrayList<Book>)savedInstanceState.getSerializable("key");
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
                booksToDisplay = new ArrayList<>();

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

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(
                                    new URL("https://kamorris.com/lab/abp/booksearch.php").openStream()
                            )
                    );
                    String books = bufferedReader.readLine();
                    bufferedReader.close();

                    JSONArray array = new JSONArray(books);

                    int i = array.length();
                    arrayList.add(new Book(10, "" + i, "Author", ""));

                } catch (Exception e) {

                }
            }
        };

        t.start();

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

