package by.it.training.library.dao;

import by.it.training.library.bean.Author;

import java.util.List;
import java.util.Map;

public interface AuthorDao {
//    void add(Author author) throws DaoException;
//    void remove(int id) throws DaoException;
//    List<Author> find() throws DaoException;

    Map<Integer, Author> getAuthors(List<Integer> booksId) throws DaoException;
    int getAuthorsCount() throws DaoException;
    List<Author> getAuthors(int pageNumber, int pageCount) throws DaoException;

}
