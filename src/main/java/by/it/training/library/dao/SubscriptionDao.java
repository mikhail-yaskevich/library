package by.it.training.library.dao;

import by.it.training.library.bean.Subscription;
import by.it.training.library.bean.SubscriptionType;

import java.util.List;

public interface SubscriptionDao {
    int getSubscriptionsCount(int userId, SubscriptionType subscriptionType) throws DaoException;
    List<Subscription> getSubscriptions(int userId, SubscriptionType subscriptionType, int pageNumber, int pageCount) throws DaoException;
    Subscription getSubscription(int userId, int id) throws DaoException;
    int addSubscription(Subscription subscription) throws DaoException;
    void updateSubscription(Subscription subscription) throws DaoException;
    void deleteSubscription(Subscription subscription) throws DaoException;

    Subscription getSubscriptionForBook(int userId, int bookId) throws DaoException;
}
