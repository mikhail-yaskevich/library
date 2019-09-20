package by.it.training.library.dao.impl;

import by.it.training.library.bean.Subscription;
import by.it.training.library.bean.SubscriptionType;
import by.it.training.library.bean.impl.SubscriptionBean;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.SubscriptionDao;
import by.it.training.library.dao.connection.ConnectionPool;
import by.it.training.library.dao.connection.ConnectionPoolException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlSubscriptionDao implements SubscriptionDao {
    private static final String SQL_subscriptions_count =
            "SELECT count(subscriptions.id) FROM subscriptions WHERE subscriptions.removed = 0 AND subscriptions.user_id = %d";

    @Override
    public int getSubscriptionsCount(int userId, SubscriptionType subscriptionType) throws DaoException {
        return MySqlDao.getCount(
                String.format(SQL_subscriptions_count + getSqlWhere(subscriptionType), userId));
    }

    private static final String SQL_subscriptions =
            "SELECT subscriptions.id, subscriptions.user_id, subscriptions.book_id, subscriptions.comment, subscriptions.rating, subscriptions.starting, subscriptions.stopping, subscriptions.reserved " +
            "FROM subscriptions WHERE subscriptions.removed = 0 AND subscriptions.user_id = ?";

    @Override
    public List<Subscription> getSubscriptions(int userId, SubscriptionType subscriptionType, int pageNumber, int pageCount) throws DaoException {
        List<Subscription> subscriptions = new ArrayList<>();

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    SQL_subscriptions + getSqlWhere(subscriptionType) + getSqlOrderBy() + MySqlDao.formatLimit(pageNumber, pageCount))) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        subscriptions.add(new SubscriptionBean(
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getInt(3),
                                resultSet.getTimestamp(6),
                                resultSet.getTimestamp(7),
                                resultSet.getTimestamp(8),
                                resultSet.getString(4),
                                resultSet.getInt(5)));
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

        return subscriptions;
    }

    private static final String SQL_subscription =
            "SELECT subscriptions.id, subscriptions.user_id, subscriptions.book_id, subscriptions.comment, subscriptions.rating, subscriptions.starting, subscriptions.stopping, subscriptions.reserved " +
                    "FROM subscriptions WHERE subscriptions.removed = 0 AND subscriptions.user_id = ? AND subscriptions.id = ?";

    @Override
    public Subscription getSubscription(int userId, int id) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_subscription )) {
                statement.setInt(1, userId);
                statement.setInt(2, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return new SubscriptionBean(
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getInt(3),
                                resultSet.getTimestamp(6),
                                resultSet.getTimestamp(7),
                                resultSet.getTimestamp(8),
                                resultSet.getString(4),
                                resultSet.getInt(5));
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    private static final String SQL_add_subscription =
            "INSERT INTO subscriptions(subscriptions.user_id, subscriptions.book_id, subscriptions.comment, subscriptions.rating, subscriptions.starting, subscriptions.stopping, subscriptions.reserved) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_last_insert_id_subscription = "SELECT last_insert_id() FROM subscriptions";

    @Override
    public int addSubscription(Subscription subscription) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_add_subscription )) {
                statement.setInt(1, subscription.getUserId());
                statement.setInt(2, subscription.getBookId());
                statement.setString(3, subscription.getComment());
                statement.setInt(4, subscription.getRating());
                statement.setTimestamp(5, subscription.getStarting());
                statement.setTimestamp(6, subscription.getStopping());
                statement.setTimestamp(7, subscription.getReserved());
                statement.execute();
            }
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_last_insert_id_subscription);
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    return 0;
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    private static final String SQL_update_subscription =
            "UPDATE subscriptions SET subscriptions.comment = ?, subscriptions.rating = ?, subscriptions.starting = ?, subscriptions.stopping = ?, subscriptions.reserved = ? " +
            "WHERE subscriptions.id = ? AND subscriptions.user_id = ?";

    @Override
    public void updateSubscription(Subscription subscription) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_update_subscription )) {
                statement.setString(1, subscription.getComment());
                statement.setInt(2, subscription.getRating());
                statement.setTimestamp(3, subscription.getStarting());
                statement.setTimestamp(4, subscription.getStopping());
                statement.setTimestamp(5, subscription.getReserved());
                statement.setInt(6, subscription.getId());
                statement.setInt(7, subscription.getUserId());
                statement.execute();
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    private static final String SQL_delete_subscription =
            "UPDATE subscriptions SET subscriptions.removed = 1 WHERE subscriptions.id = ? AND subscriptions.user_id = ?";

    @Override
    public void deleteSubscription(Subscription subscription) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_delete_subscription )) {
                statement.setInt(1, subscription.getId());
                statement.setInt(2, subscription.getUserId());
                statement.execute();
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    private static final String SQL_subscription_for_book =
            "SELECT subscriptions.id, subscriptions.user_id, subscriptions.book_id, subscriptions.comment, subscriptions.rating, subscriptions.starting, subscriptions.stopping, subscriptions.reserved " +
            "FROM subscriptions WHERE subscriptions.removed = 0 AND subscriptions.user_id = ? AND subscriptions.book_id = ? AND " +
            "subscriptions.stopping IS NULL";

    @Override
    public Subscription getSubscriptionForBook(int userId, int bookId) throws DaoException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_subscription_for_book )) {
                statement.setInt(1, userId);
                statement.setInt(2, bookId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return new SubscriptionBean(
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getInt(3),
                                resultSet.getTimestamp(6),
                                resultSet.getTimestamp(7),
                                resultSet.getTimestamp(8),
                                resultSet.getString(4),
                                resultSet.getInt(5));
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    private String getSqlWhere(SubscriptionType subscriptionType) {
        switch (subscriptionType) {
            case OPENED: {
                return " AND subscriptions.starting IS NOT NULL AND subscriptions.stopping IS NULL";
            }
            case CLOSED: {
                return " AND subscriptions.starting IS NOT NULL AND subscriptions.stopping IS NOT NULL";
            }
            case RESERVED: {
                return " AND subscriptions.reserved IS NOT NULL AND subscriptions.starting IS NULL";
            }
            default: {
                return "";
            }
        }
    }

    private String getSqlOrderBy() {
        return " ORDER BY subscriptions.id DESC";
    }
}
