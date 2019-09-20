package by.it.training.library.dao.impl;

import by.it.training.library.bean.Author;
import by.it.training.library.bean.impl.AuthorBean;
import by.it.training.library.controller.PageConstant;
import by.it.training.library.dao.AuthorDao;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.connection.ConnectionPool;
import by.it.training.library.dao.connection.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MySqlAuthorDao implements AuthorDao {

    private static final String SQL_authors_only =
            "SELECT authors.id, authors.firstname, authors.lastname, " +
                    "(SELECT GROUP_CONCAT(books_authors.book_id) FROM books_authors WHERE books_authors.author_id = authors.id), authors.born, authors.dead " +
            "FROM authors WHERE authors.removed = 0;";

    private static final String SQL_authors_for_books =
            "SELECT authors.id, authors.firstname, authors.lastname, " +
                    "(select group_concat(books_authors.book_id) from books_authors where books_authors.author_id = authors.id and books_authors.book_id in (?)) booksid, authors.born, authors.dead " +
            "FROM authors JOIN books_authors ON authors.id = books_authors.author_id WHERE removed = 0 AND books_authors.book_id IN (?);";

    @Override
    public Map<Integer, Author> getAuthors(List<Integer> booksId) throws DaoException {
        Map<Integer, Author> authors = new HashMap<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            boolean onlyForBooks = Objects.isNull(booksId) && (booksId.size() > 0);
            try (PreparedStatement statement = connection.prepareStatement(onlyForBooks ? SQL_authors_for_books : SQL_authors_only)) {
                if (onlyForBooks) {
                    statement.setString(1,
                            booksId.stream().map(Object::toString).collect(Collectors.joining(",")));
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        authors.put(resultSet.getInt(1),
                        new AuthorBean(resultSet.getInt(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5), resultSet.getTimestamp(6)));
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

        return authors;
    }

    private static final String SQL_authors_count = "SELECT count(authors.id) FROM authors";

    @Override
    public int getAuthorsCount() throws DaoException {
        return MySqlDao.getCount(SQL_authors_count);
    }

    private static final String SQL_authors =
            "SELECT authors.id, authors.firstname, authors.lastname, " +
                    "(SELECT GROUP_CONCAT(books_authors.book_id) FROM books_authors WHERE books_authors.author_id = authors.id), authors.born, authors.dead " +
                    "FROM authors WHERE authors.removed = 0 ORDER BY authors.lastname, authors.firstname";

    @Override
    public List<Author> getAuthors(int pageNumber, int pageCount) throws DaoException {
        List<Author> authors = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_authors + MySqlDao.formatLimit(pageNumber, pageCount))) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        authors.add(new AuthorBean(resultSet.getInt(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5), resultSet.getTimestamp(6)));
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

        return authors;
    }

    private static final String SQL_author =
            "SELECT authors.id, authors.firstname, authors.lastname, " +
                    "(SELECT GROUP_CONCAT(books_authors.book_id) FROM books_authors WHERE books_authors.author_id = authors.id), authors.born, authors.dead " +
            "FROM authors WHERE authors.removed = 0 AND authors.id = ?";

    @Override
    public Author getAuthor(int authorId) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_author)) {
                statement.setInt(1, authorId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return new AuthorBean(resultSet.getInt(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5), resultSet.getTimestamp(6));
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return null;
    }
}
