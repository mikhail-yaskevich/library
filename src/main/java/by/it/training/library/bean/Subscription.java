package by.it.training.library.bean;

import java.sql.Timestamp;
import java.util.Map;

public interface Subscription {
    int getId();
    int getUserId();
    int getBookId();
    Timestamp getStarting();
    Timestamp getStopping();
    Timestamp getReserved();
    String getComment();
    int getRating();
    void setBook(Map<Integer, Book> books);
    Book getBook();
}
