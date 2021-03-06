package by.it.training.library.dao.impl;

import by.it.training.library.controller.PageConstant;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.connection.ConnectionPool;
import by.it.training.library.dao.connection.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

final class MySqlDao {

    static int getCount(String sql) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

        return 0;
    }

    static String formatLimit(int pageNumber, int pageCount) {
        return (((pageNumber > -1) && (pageCount > 0)) ?
                String.format(" LIMIT %d, %d", pageNumber * PageConstant.MAX_COUNT_OBJECTS_ON_PAGE, PageConstant.MAX_COUNT_OBJECTS_ON_PAGE) : "");
    }
}
