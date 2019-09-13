package by.it.training.library.dao;

import by.it.training.library.bean.Author;
import by.it.training.library.bean.Book;

import java.util.List;
import java.util.Map;

public interface BookDao {
//    void add(Book book) throws DaoException;
//    void remove(int id) throws DaoException;
//    List<Book> find(String text) throws DaoException;

    List<Book> getNewAddedBooks()throws DaoException;

    List<Book> getLastReadBooks() throws DaoException;
    int getBooksCount() throws DaoException;
    List<Book> getBooks(int pageNumber, int pageCount) throws DaoException;

    //Map<Integer, Book> getBooks(List<Integer> idAuthors) throws DaoException;
}
