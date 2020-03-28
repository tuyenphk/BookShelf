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

    private ArrayList<HashMap> getBooks() {
        ArrayList<HashMap> arrayList = new ArrayList<>();

        HashMap<String, String> book1 = new HashMap<String, String>();
        book1.put("The Hitchhiker's Guide to the Galaxy", "Douglas Adams");
        arrayList.add(book1);

        HashMap<String, String> book2 = new HashMap<String, String>();
        book2.put("Eragon", "Christopher Paolini");
        arrayList.add(book2);

        HashMap<String, String> book3 = new HashMap<String, String>();
        book3.put("A Walk in the Woods", "Bill Bryson");
        arrayList.add(book3);

        HashMap<String, String> book4 = new HashMap<String, String>();
        book4.put("A Tale of Two Cities", "Charles Dickens");
        arrayList.add(book4);

        HashMap<String, String> book5 = new HashMap<String, String>();
        book5.put("Ringworld", "Larry Niven");
        arrayList.add(book5);

        HashMap<String, String> book6 = new HashMap<String, String>();
        book6.put("The Restaurant at the End of the Universe", "Douglas Adams");
        arrayList.add(book6);

        HashMap<String, String> book7 = new HashMap<String, String>();
        book7.put("Life, the Universe and Everything", "Douglas Adams");
        arrayList.add(book7);

        HashMap<String, String> book8 = new HashMap<String, String>();
        book8.put("So Long, and Thanks for All the Fish", "Douglas Adams");
        arrayList.add(book8);

        HashMap<String, String> book9 = new HashMap<String, String>();
        book9.put("American Gods", "Neil Gaiman");
        arrayList.add(book9);

        HashMap<String, String> book10 = new HashMap<String, String>();
        book10.put("A Wizard Of Earthsea", "Ursula K. Le Guin");
        arrayList.add(book10);

        return arrayList;
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

