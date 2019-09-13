package by.it.training.library.bean.impl;

import by.it.training.library.bean.Author;
import by.it.training.library.bean.Book;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorBean implements Author {

    private int id;
    private String firstname;
    private String lastname;
    private List<Integer> booksId = new ArrayList<>();

    public AuthorBean(int id, String firstname, String lastname, String booksId) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        if (booksId != null && !booksId.isEmpty()) {
            this.booksId.addAll(
                    Arrays.asList(booksId.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public Timestamp getBorn() {
        return null;
    }

    @Override
    public Timestamp getDead() {
        return null;
    }

    @Override
    public String getReview() {
        return null;
    }

    @Override
    public List<Book> getBooks() {
        return null;
    }

    @Override
    public List<Integer> getBooksId() {
        return booksId;
    }
}
