package by.it.training.library.bean.impl;

import by.it.training.library.bean.Author;
import by.it.training.library.bean.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookBean implements Book {

    private int id;
    private String title;
    private String review;
    private List<Author> authors = new ArrayList<>();
    private List<Integer> authorsId = new ArrayList<>();

    public BookBean(int id, String title, String authorsId) {
        this.id = id;
        this.title = title;
        if (authorsId != null && !authorsId.isEmpty()) {
            this.authorsId.addAll(
                    Arrays.asList(authorsId.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getReview() {
        return review;
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public List<Integer> getAuthorId() {
        return authorsId;
    }

    @Override
    public void setLinksToAuthors(Map<Integer, Author> authors) {
        for (Integer authorid : authorsId) {
            this.authors.add(authors.get(authorid));
        }
    }
}
