package by.it.training.library.service;

import by.it.training.library.bean.User;

import java.util.List;

public interface UserService {
    User registration(User userParams) throws ServiceException;
    boolean registrationCheck(User userParams) throws ServiceException;
    User authorization(String login, String password) throws ServiceException;
    int getUsersCount() throws ServiceException;
    List<User> getUsers(int pageNumber, int pageCount) throws ServiceException;
}
