package by.it.training.library.dao;

import by.it.training.library.bean.User;

public interface UserDao {

    void registration(User userParams) throws DaoException;
    boolean registrationCheck(User userParams) throws DaoException;
    User authorization(String login, String password) throws DaoException;



}
