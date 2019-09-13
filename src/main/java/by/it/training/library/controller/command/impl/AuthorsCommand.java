package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.Author;
import by.it.training.library.controller.PageConstant;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.AuthorService;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthorsCommand extends BaseCommand {
    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=authors");
            request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/common/authors.jsp");

            AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

            int pageNumber;
            try {
                pageNumber = Integer.parseInt(request.getParameter("page")) - 1;
            } catch (NumberFormatException e) {
                pageNumber = 0;
            }

            int pageCount;
            try {
                int booksCount = authorService.getAuthorsCount();
                pageCount = booksCount / PageConstant.MAX_COUNT_BOOKS_ON_PAGE + ((booksCount % PageConstant.MAX_COUNT_BOOKS_ON_PAGE > 0) ? 1 : 0);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            pageNumber = (pageNumber >= pageCount) ? pageCount : ((pageNumber < 0) ? 0 : pageNumber);

            List<Author> authors;
            try {
                authors = authorService.getAuthors(pageNumber, pageCount);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            request.setAttribute("authors", authors);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("pageNumber", pageNumber + 1);
            request.setAttribute("pageCommand", "authors");
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=authors&page=" + (pageNumber + 1));
        }
    }
}
