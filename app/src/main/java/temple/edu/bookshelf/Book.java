package temple.edu.bookshelf;

public class Book {
    int id;
    String title;
    String author;
    String coverURL;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        coverURL = "";
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

}
