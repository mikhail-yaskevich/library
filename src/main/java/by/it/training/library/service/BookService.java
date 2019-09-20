package by.it.training.library.service;

import by.it.training.library.bean.Book;

import java.util.List;

public interface BookService {
    List<Book> getNewAddedBooks() throws ServiceException;

    List<Book> getLastReadBooks() throws ServiceException;
    int getBooksCount() throws ServiceException;
    List<Book> getBooks(int pageNumber, int pageCount) throws ServiceException;
    Book getBook(int id) throws ServiceException;
    List<Book> getBooks(int authorId) throws ServiceException;
    //List<Book> findBooks(String[] words) throws ServiceException;
}
