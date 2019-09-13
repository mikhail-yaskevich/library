package by.it.training.library.bean;

import java.sql.Timestamp;

public interface Subscription {
    int getId();
    int getUserId();
    int getBookId();
    Timestamp getStarting();
    Timestamp getStopping();
    Timestamp getReserved();
    String getComment();
    int getRating();
}
