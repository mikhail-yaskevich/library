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

    int getFindBooksCount(String searchText) throws ServiceException;;
    List<Book> getFindBooks(String searchText, int pageNumber, int pageCount) throws ServiceException;;
}
