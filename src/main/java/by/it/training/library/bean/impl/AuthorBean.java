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
    private List<Book> books;
    private Timestamp born;
    private Timestamp dead;

    public AuthorBean(int id, String firstname, String lastname, String booksId, Timestamp born, Timestamp dead) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        if (booksId != null && !booksId.isEmpty()) {
            this.booksId.addAll(
                    Arrays.asList(booksId.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        this.born = born;
        this.dead = dead;
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
        return born;
    }

    @Override
    public Timestamp getDead() {
        return dead;
    }

    @Override
    public String getReview() {
        return null;
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<Integer> getBooksId() {
        return booksId;
    }
}
