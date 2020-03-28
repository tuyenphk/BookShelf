package temple.edu.bookshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookDetailsFragment extends Fragment {


    public BookDetailsFragment() {
        // Required empty public constructor
    }

    HashMap<String, String> book;
    TextView titleView;
    TextView authorView;

    final static String BOOK_DETAILS_KEY = "book_details_key";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            book = (HashMap)bundle.getSerializable(BOOK_DETAILS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        titleView = v.findViewById(R.id.titleView);
        authorView = v.findViewById(R.id.authorView);

        if(book != null) {
            displayBook(book);
        }

        return v;
    }

    public static BookDetailsFragment newInstance(HashMap book) {
        BookDetailsFragment newFragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BOOK_DETAILS_KEY, book);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    public void displayBook(HashMap<String, String> book) {
        String title = new String();
        String author = new String();

        for(String i : book.keySet()) {
            title = i;
        }

        for(String i : book.values()) {
            author = i;
        }

        titleView.setText(title);
        authorView.setText(author);
    }

}

