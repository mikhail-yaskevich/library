package by.it.training.library.bean;

import java.sql.Timestamp;
import java.util.List;

public interface Author {
    int getId();
    String getFirstname();
    String getLastname();
    Timestamp getBorn();
    Timestamp getDead();
    String getReview();
    List<Book> getBooks();
    void setBooks(List<Book> books);
    List<Integer> getBooksId();
}
