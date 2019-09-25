package by.it.training.library.controller;

public final class PageName {

    private PageName() {
    }

    public static final String WELCOME = "library";//index.jsp

    public static final String CONTROLLER = "/controller";

    public static final String USERS = "controller/users";
    public static final String SUBSCRIPTION = "controller/subscriptions";
    public static final String BOOKS = "controller/books";
    public static final String AUTHORS = "controller/authors";

    public static final String GET_GENERAL = "/main?command=general";
    public static final String PAGE_GENERAL = "/WEB-INF/jsp/common/general.jsp";
    public static final String GET_LOGIN = "/main?command=login";
    public static final String PAGE_LOGIN = "/WEB-INF/jsp/common/login.jsp";

    public static final String GET_REGISTRATION = "/main?command=registration";
    public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/common/registration.jsp";

}
