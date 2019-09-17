package by.it.training.library.service;

import by.it.training.library.bean.Subscription;
import by.it.training.library.bean.SubscriptionType;

import java.util.List;

public interface SubscriptionService {
    int getSubscriptionsCount(int userId, SubscriptionType subscriptionType) throws ServiceException;
    List<Subscription> getSubscriptions(int userId, SubscriptionType subscriptionType, int pageNumber, int pageCount) throws ServiceException;
    Subscription getSubscription(int userId, int id) throws ServiceException;
    int addSubscription(Subscription subscription) throws ServiceException;
    void updateSubscription(Subscription subscription) throws ServiceException;
    void deleteSubscription(Subscription subscription) throws ServiceException;
}
