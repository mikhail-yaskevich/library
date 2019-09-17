package by.it.training.library.bean.impl;

import by.it.training.library.bean.Book;
import by.it.training.library.bean.Subscription;

import java.sql.Timestamp;
import java.util.Map;

public class SubscriptionBean implements Subscription {

    private int id;
    private int userId;
    private int bookId;
    private Timestamp starting;
    private Timestamp stopping;
    private Timestamp reserved;
    private String comment;
    private int rating;
    private Book book;

    public SubscriptionBean() {
    }

    public SubscriptionBean(int id, int userId, int bookId, Timestamp starting, Timestamp stopping, Timestamp reserved, String comment, int rating) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.starting = starting;
        this.stopping = stopping;
        this.reserved = reserved;
        this.comment = comment;
        this.rating = rating;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Map<Integer, Book> books) {
        this.book = books.get(bookId);
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setStarting(Timestamp starting) {
        this.starting = starting;
    }

    public void setStopping(Timestamp stopping) {
        this.stopping = stopping;
    }

    public void setReserved(Timestamp reserved) {
        this.reserved = reserved;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public int getBookId() {
        return bookId;
    }

    @Override
    public Timestamp getStarting() {
        return starting;
    }

    @Override
    public Timestamp getStopping() {
        return stopping;
    }

    @Override
    public Timestamp getReserved() {
        return reserved;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public int getRating() {
        return rating;
    }
}
