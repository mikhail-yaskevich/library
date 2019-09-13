package by.it.training.library.bean;

import java.util.List;
import java.util.Map;

public interface Book {
    int getId();
    String getTitle();
    String getReview();
    List<Author> getAuthors();
    List<Integer> getAuthorId();

    void setLinksToAuthors(Map<Integer, Author> authors);
}
