package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.User;
import by.it.training.library.bean.UserType;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.BookService;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;
import by.it.training.library.service.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.EnumSet;
import java.util.Set;

public class BookCommand extends BaseCommand {

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.GUEST, UserType.READER, UserType.ADMIN);
    }

    @Override
    public void doDefault(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=book&book=");
            request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/guest/book.jsp");

            int bookId = Integer.parseInt(request.getParameter("book"));

            BookService bookService = ServiceProvider.getInstance().getBookService();
            try {
                request.setAttribute("book", bookService.getBook(bookId));
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=book&book=" + bookId);
        }
    }

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        doDefault(request, response);

        if ("GET".equals(request.getMethod())) {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute(SessionAttributeName.USER);
            UserType userType = UserType.valueOf((String) session.getAttribute(SessionAttributeName.USER_TYPE));

            switch (userType) {
                case READER: {
                    int bookId = Integer.parseInt(request.getParameter("book"));

                    SubscriptionService subscriptionService = ServiceProvider.getInstance().getSubscriptionService();

                    try {
                        request.setAttribute("subscription", subscriptionService.getSubscriptionForBook(user.getId(), bookId));
                    } catch (ServiceException e) {
                        throw new CommandException(e);
                    }
                    break;
                }
            }
        }
    }
}
