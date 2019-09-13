package by.it.training.library.service.impl;

import by.it.training.library.bean.Author;
import by.it.training.library.bean.Book;
import by.it.training.library.dao.AuthorDao;
import by.it.training.library.dao.BookDao;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.DaoProvider;
import by.it.training.library.service.BookService;
import by.it.training.library.service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = DaoProvider.getInstance().getBookDao();
    private AuthorDao authorDao = DaoProvider.getInstance().getAuthorDao();

    @Override
    public List<Book> getLastReadBooks() throws ServiceException {
        try {
            return setLinksToAuthors(bookDao.getLastReadBooks());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getBooksCount() throws ServiceException {
        try {
            return bookDao.getBooksCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getBooks(int pageNumber, int pageCount) throws ServiceException {
        try {
            return setLinksToAuthors(bookDao.getBooks(pageNumber, pageCount));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getNewAddedBooks() throws ServiceException {
        try {
            return setLinksToAuthors(bookDao.getNewAddedBooks());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private List<Book> setLinksToAuthors(List<Book> books) throws ServiceException {
        try {
            List<Integer> booksId = new ArrayList<>();
            for (Book book : books) {
                booksId.add(book.getId());
            }
            Map<Integer, Author> authors = authorDao.getAuthors(booksId);
            for (Book book : books) {
                book.setLinksToAuthors(authors);
            }
            return books;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
