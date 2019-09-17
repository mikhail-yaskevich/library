package by.it.training.library.service;

import by.it.training.library.service.impl.AuthorServiceImpl;
import by.it.training.library.service.impl.BookServiceImpl;
import by.it.training.library.service.impl.SubscriptionServiceImpl;
import by.it.training.library.service.impl.UserServiceImpl;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    public static ServiceProvider getInstance() {
        return instance;
    }

    private ServiceProvider() {
    }

    private UserService userService = new UserServiceImpl();

    public UserService getUserService() {
        return userService;
    }

    private BookService bookService = new BookServiceImpl();

    public  BookService getBookService() {
        return bookService;
    }

    private AuthorService authorService = new AuthorServiceImpl();

    public  AuthorService getAuthorService() {
        return authorService;
    }

    private SubscriptionService subscriptionService = new SubscriptionServiceImpl();

    public SubscriptionService getSubscriptionService() {
        return subscriptionService;
    }
}
