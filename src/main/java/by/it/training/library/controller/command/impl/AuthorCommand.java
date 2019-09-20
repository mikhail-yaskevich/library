package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.Author;
import by.it.training.library.bean.User;
import by.it.training.library.bean.UserType;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class AuthorCommand extends BaseCommand {
    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=author&author=");
            request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/guest/author.jsp");

            int authorId = Integer.parseInt(request.getParameter("author"));

            AuthorService authorService = ServiceProvider.getInstance().getAuthorService();
            BookService bookService = ServiceProvider.getInstance().getBookService();
            try {
                Author author = authorService.getAuthor(authorId);
                author.setBooks(bookService.getBooks(authorId));
                request.setAttribute("author", author);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

//            HttpSession session = request.getSession(false);
//            if (Objects.nonNull(session) &&
//                    Objects.nonNull(session.getAttribute(SessionAttributeName.USER)) &&
//                    Objects.nonNull(session.getAttribute(SessionAttributeName.USER_TYPE))) {
//                String attribute = ((String) session.getAttribute(SessionAttributeName.USER_TYPE)).toUpperCase();
//                if (UserType.READER.name().equals(attribute)) {
//                    User user = (User) session.getAttribute(SessionAttributeName.USER);
//
//                    SubscriptionService subscriptionService = ServiceProvider.getInstance().getSubscriptionService();
//
//                    try {
//                        request.setAttribute("subscription", subscriptionService.getSubscriptionForBook(user.getId(), bookId));
//                    } catch (ServiceException e) {
//                        throw new CommandException(e);
//                    }
//                }
//            }

            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=author&author=" + authorId);
        }
    }
}
