// Tuyen Pham 915591991 CIS3515//

package temple.edu.bookshelf;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class BookApdater extends BaseAdapter {
    private Context c;
    private ArrayList<Book> data;

    public BookApdater(Context c, ArrayList<Book> data) {
        this.c = c;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView t;
        if(convertView == null) {
            t = new TextView(c);
        } else {
            t = (TextView)convertView;
        }

        String title = new String();
        String author = new String();

        Book h = (Book)getItem(position);
        title = h.getTitle();
        author = h.getAuthor();

        t.setText(title + "\n" + author);
        return t;
    }
}

