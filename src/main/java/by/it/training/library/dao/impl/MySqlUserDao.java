package by.it.training.library.dao.impl;

import by.it.training.library.bean.Role;
import by.it.training.library.bean.UserType;
import by.it.training.library.bean.impl.UserBean;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.UserDao;
import by.it.training.library.dao.connection.ConnectionPool;
import by.it.training.library.bean.User;
import by.it.training.library.dao.connection.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


public class MySqlUserDao implements UserDao {

    private static final String SQL_authorization =
            "SELECT users.id, users.firstname, users.lastname, users.removed, users.beginning, users.ending, " +
                    "roles.addbook, roles.editbook, roles.readbook, roles.reservebook, roles.takebook, roles.removebook, " +
                    "roles.adduser, roles.edituser, roles.removeuser, users.login, users.email, roles.name " +
                    "FROM users left join roles on users.role_id = roles.id " +
                    "WHERE login = ? AND password = SHA2(?, 256) AND removed = 0";

    @Override
    public User authorization(String login, String password) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_authorization)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new UserBean(
                            resultSet.getInt(1),
                            resultSet.getString(16),
                            "",
                            resultSet.getString(17),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getTimestamp(5),
                            resultSet.getTimestamp(6),
                            EnumSet.of(
                                    Role.NONE,
                                    Role.ADD_BOOK.enable(resultSet.getBoolean(7)),
                                    Role.EDIT_BOOK.enable(resultSet.getBoolean(8)),
                                    Role.READ_BOOK.enable(resultSet.getBoolean(9)),
                                    Role.RESERVE_BOOK.enable(resultSet.getBoolean(10)),
                                    Role.TAKE_BOOK.enable(resultSet.getBoolean(11)),
                                    Role.REMOVE_BOOK.enable(resultSet.getBoolean(12)),
                                    Role.ADD_USER.enable(resultSet.getBoolean(13)),
                                    Role.EDIT_USER.enable(resultSet.getBoolean(14)),
                                    Role.REMOVE_USER.enable(resultSet.getBoolean(15))
                            ),
                            UserType.valueOf(resultSet.getString(18).toUpperCase())
                    );
                    return user;
                } else {
                    return null;
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    private static final String SQL_users_count = "SELECT count(users.id) FROM users";

    @Override
    public int getUsersCount() throws DaoException {
        return MySqlDao.getCount(SQL_users_count);
    }

    private static final String SQL_users =
            "SELECT users.id, users.firstname, users.lastname, users.removed, users.beginning, users.ending, " +
                    "users.login, users.email, roles.name " +
            "FROM users left join roles on users.role_id = roles.id " +
            "WHERE removed = 0 " +
            "ORDER BY users.lastname, users.firstname ";

    @Override
    public List<User> getUsers(int pageNumber, int pageCount) throws DaoException {
        List<User> users = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_users + MySqlDao.formatLimit(pageNumber, pageCount))) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        users.add(
                                new UserBean(
                                        resultSet.getInt(1),
                                        resultSet.getString(7),
                                        "",
                                        resultSet.getString(8),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getTimestamp(5),
                                        resultSet.getTimestamp(6),
                                        null,
                                        UserType.valueOf(resultSet.getString(9).toUpperCase())
                                )
                        );
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

        return users;
    }

    private static final String SQL_registration_check = "SELECT id FROM users WHERE login = ?";
    private static final String SQL_registration =
            "INSERT INTO users(firstname, lastname, role_id, login, password, beginning, email) " +
                    "VALUES(?, ?, ?, ?, SHA2(?, 256), current_timestamp(), ?)";

    @Override
    public void registration(User userParams) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_registration)) {
                statement.setString(1, userParams.getFirstName());
                statement.setString(2, userParams.getLastName());
                // todo сделать предварительную загрузку ролей и их установку
                statement.setInt(3, 2);// просто читатель
                statement.setString(4, userParams.getLogin());
                statement.setString(5, userParams.getPassword());
                statement.setString(6, userParams.getEmail());
                statement.execute();
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean registrationCheck(User userParams) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_registration_check)) {
                statement.setString(1, userParams.getLogin());
                try (ResultSet resultSet = statement.executeQuery()) {
                    return !resultSet.next();
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }
}
