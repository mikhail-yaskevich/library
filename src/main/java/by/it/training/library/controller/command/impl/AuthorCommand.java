package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.Author;
import by.it.training.library.bean.UserType;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;
import java.util.Set;

public class AuthorCommand extends BaseCommand {

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.GUEST, UserType.READER, UserType.ADMIN);
    }

    @Override
    public void doDefault(HttpServletRequest request, HttpServletResponse response) throws CommandException {
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

            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=author&author=" + authorId);
        }
    }

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        doDefault(request, response);
    }
}
