package by.it.training.library.dao.impl;

import by.it.training.library.bean.Book;
import by.it.training.library.bean.impl.BookBean;
import by.it.training.library.controller.PageConstant;
import by.it.training.library.dao.BookDao;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.connection.ConnectionPool;
import by.it.training.library.dao.connection.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MySqlBookDao implements BookDao {

    private static final String SQL_new_added_books =
            "SELECT books.id, books.title, " +
                    "(SELECT GROUP_CONCAT(books_authors.author_id) FROM books_authors WHERE books_authors.book_id = books.id) authorsid " +
            "FROM books WHERE books.added >= ADDDATE(NOW(), INTERVAL -30 DAY) ORDER BY books.added DESC LIMIT 20";

    @Override
    public List<Book> getNewAddedBooks() throws DaoException {
        return getBooksFrom(SQL_new_added_books);
    }

    private static final String SQL_last_read_books =
            "SELECT DISTINCT books.id, books.title, " +
                    "(SELECT GROUP_CONCAT(books_authors.author_id) FROM books_authors WHERE books_authors.book_id = books.id) authorsid " +
            "FROM subscriptions LEFT JOIN books ON subscriptions.book_id = books.id " +
            "WHERE subscriptions.starting IS NOT NULL AND subscriptions.stopping IS NULL " +
            "ORDER BY subscriptions.starting DESC LIMIT ";

    public List<Book> getLastReadBooks() throws DaoException {
        return getBooksFrom(SQL_last_read_books + PageConstant.MAX_COUNT_BOOKS_ON_PAGE);
    }

    private static final String SQL_books_count = "SELECT count(books.id) FROM books";

    public int getBooksCount() throws DaoException {
        return MySqlDao.getCount(SQL_books_count);
    }

    private static final String SQL_books =
            "SELECT books.id, books.title, " +
                    "(SELECT GROUP_CONCAT(books_authors.author_id) FROM books_authors WHERE books_authors.book_id = books.id) authorsid " +
            "FROM books " +
            "ORDER BY books.title ASC";

    public List<Book> getBooks(int pageNumber, int pageCount) throws DaoException {
        return getBooksFrom(SQL_books + MySqlDao.formatLimit(pageNumber, pageCount));
    }

    private static final String SQL_books_for_books =
            "SELECT books.id, books.title, " +
                    "(SELECT GROUP_CONCAT(books_authors.author_id) FROM books_authors WHERE books_authors.book_id = books.id) authorsid " +
            "FROM books " +
            "WHERE books.id IN (%s)";

    @Override
    public Map<Integer, Book> getBooks(List<Integer> booksId) throws DaoException {
        Map<Integer, Book> books = new HashMap<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        //если booksId пусто, то...
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    String.format(SQL_books_for_books, booksId.stream().map(Object::toString).collect(Collectors.joining(","))))) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        books.put(resultSet.getInt(1),
                                new BookBean(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

        return books;
    }

    private static final String SQL_author_books =
            "SELECT books.id, books.title, " +
                    "(SELECT GROUP_CONCAT(books_authors.author_id) FROM books_authors WHERE books_authors.book_id = books.id ) authorsid " +
            "FROM books JOIN books_authors ON books.id = books_authors.book_id " +
            "WHERE books_authors.author_id = ? " +
            "ORDER BY books.title ASC";

    @Override
    public List<Book> getBooks(int authorId) throws DaoException {
        List<Book> books = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_author_books)) {
                statement.setInt(1, authorId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        books.add(new BookBean(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

        return books;
    }

    private List<Book> getBooksFrom(String sql) throws DaoException {
        List<Book> books = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        books.add(
                                new BookBean(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

        return books;
    }

    private static final String SQL_find_books = "";

    public List<Book> findBooks(String[] words) throws DaoException {
        return null;
    }
}
