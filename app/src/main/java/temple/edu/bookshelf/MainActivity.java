package temple.edu.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {

    ArrayList<HashMap> books;
    boolean twoPanes;
    BookDetailsFragment bookDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPanes = (findViewById(R.id.detailsFrame) != null);

        books = getBooks();
        BookListFragment bookListFragment = BookListFragment.newInstance(books);

        FragmentManager f = getSupportFragmentManager();
        FragmentTransaction t = f.beginTransaction();
        t.add(R.id.frame1, bookListFragment);

        if(twoPanes) {
            bookDetailsFragment = new BookDetailsFragment();
            t.add(R.id.detailsFrame, bookDetailsFragment);
        }

        t.commit();

        f.executePendingTransactions();
    }



    @Override
    public void bookSelected(int index) {
        FragmentManager f = getSupportFragmentManager();
        FragmentTransaction t = f.beginTransaction();

        if(twoPanes) {
            bookDetailsFragment.displayBook(books.get(index));
        } else {
            t.addToBackStack(null).replace(R.id.frame1, BookDetailsFragment.newInstance(books.get(index)));
        }
        t.commit();
    }
}

