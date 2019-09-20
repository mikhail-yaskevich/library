package by.it.training.library.service.impl;

import by.it.training.library.bean.Author;
import by.it.training.library.dao.AuthorDao;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.DaoProvider;
import by.it.training.library.service.AuthorService;
import by.it.training.library.service.ServiceException;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao = DaoProvider.getInstance().getAuthorDao();

    @Override
    public int getAuthorsCount() throws ServiceException {
        try {
            return authorDao.getAuthorsCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Author> getAuthors(int pageNumber, int pageCount) throws ServiceException {
        try {
            return authorDao.getAuthors(pageNumber, pageCount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Author getAuthor(int authorId) throws ServiceException {
        try {
            return authorDao.getAuthor(authorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
