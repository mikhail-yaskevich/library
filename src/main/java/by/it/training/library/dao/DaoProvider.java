package by.it.training.library.dao;

import by.it.training.library.dao.impl.MySqlAuthorDao;
import by.it.training.library.dao.impl.MySqlBookDao;
import by.it.training.library.dao.impl.MySqlUserDao;

public final class DaoProvider {

    private static final DaoProvider instance = new DaoProvider();

    public static DaoProvider getInstance() {
        return instance;
    }

    private DaoProvider() {
    }

    private UserDao userDAO = new MySqlUserDao();

    public UserDao getUserDAO() {
        return userDAO;
    }

    private BookDao bookDao = new MySqlBookDao();

    public BookDao getBookDao() {
        return bookDao;
    }

    private AuthorDao authorDao = new MySqlAuthorDao();

    public AuthorDao getAuthorDao() {
        return authorDao;
    }
}
