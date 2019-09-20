package by.it.training.library.service;

import by.it.training.library.bean.Author;

import java.util.List;

public interface AuthorService {
    int getAuthorsCount() throws ServiceException;
    List<Author> getAuthors(int pageNumber, int pageCount) throws ServiceException;
    Author getAuthor(int authorId) throws ServiceException;
}
