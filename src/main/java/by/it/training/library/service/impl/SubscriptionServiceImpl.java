package by.it.training.library.service.impl;

import by.it.training.library.bean.Book;
import by.it.training.library.bean.Subscription;
import by.it.training.library.bean.SubscriptionType;
import by.it.training.library.dao.BookDao;
import by.it.training.library.dao.DaoException;
import by.it.training.library.dao.DaoProvider;
import by.it.training.library.dao.SubscriptionDao;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.SubscriptionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SubscriptionServiceImpl implements SubscriptionService {
    private SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();

    @Override
    public int getSubscriptionsCount(int userId, SubscriptionType subscriptionType) throws ServiceException {
        try {
            return subscriptionDao.getSubscriptionsCount(userId, subscriptionType);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Subscription> getSubscriptions(int userId, SubscriptionType subscriptionType, int pageNumber, int pageCount) throws ServiceException {
        try {
            return setLinksToBooks(subscriptionDao.getSubscriptions(userId, subscriptionType, pageNumber, pageCount));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Subscription getSubscription(int userId, int id) throws ServiceException {
        try {
            return setLinksToBooks(Arrays.asList(subscriptionDao.getSubscription(userId, id))).get(0);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int addSubscription(Subscription subscription) throws ServiceException {
        try {
            return subscriptionDao.addSubscription(subscription);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSubscription(Subscription subscription) throws ServiceException {
        try {
            subscriptionDao.updateSubscription(subscription);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteSubscription(Subscription subscription) throws ServiceException {
        try {
            subscriptionDao.deleteSubscription(subscription);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Subscription getSubscriptionForBook(int userId, int bookId) throws ServiceException {
        try {
            return subscriptionDao.getSubscriptionForBook(userId, bookId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private List<Subscription> setLinksToBooks(List<Subscription> subscriptions) throws ServiceException {
        List<Integer> booksId = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            booksId.add(subscription.getBookId());
        }
        BookDao bookDao = DaoProvider.getInstance().getBookDao();
        try {

            Map<Integer, Book> books = bookDao.getBooks(booksId);
            for (Subscription subscription : subscriptions) {
                subscription.setBook(books);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return subscriptions;
    }
}
