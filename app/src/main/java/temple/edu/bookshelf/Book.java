package temple.edu.bookshelf;

import android.os.Parcel;
import android.os.Parcelable;

/*
Book class to represent a book. Implements the Parcelable interface
so that a book can be saved inside a bundle object
 */
public class Book implements Parcelable {

    public final static String JSON_ID = "book_id";
    public final static String JSON_TITLE = "title";
    public final static String JSON_AUTHOR = "author";
    public final static String JSON_COVER_URL = "cover_url";

    private int id;
    private String title, author, coverUrl;

    public Book(int id, String title, String author, String coverUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readString();
        coverUrl = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(coverUrl);
    }
}
