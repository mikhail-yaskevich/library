package by.it.training.library.service.impl;

import by.it.training.library.bean.User;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.DaoProvider;
import by.it.training.library.dao.UserDao;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = DaoProvider.getInstance().getUserDAO();

    @Override
    public User authorization(String login, String password) throws ServiceException {

        // todo добавить проверку данных при авторизации

        try {
            return userDao.authorization(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User registration(User userParams) throws ServiceException {

        // todo добавить проверку данных при регистрации

        try {
            userDao.registration(userParams);
            return userDao.authorization(userParams.getLogin(), userParams.getPassword());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean registrationCheck(User userParams) throws ServiceException {
        try {
            return userDao.registrationCheck(userParams);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
