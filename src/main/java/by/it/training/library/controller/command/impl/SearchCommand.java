package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.Book;
import by.it.training.library.bean.UserType;
import by.it.training.library.controller.PageConstant;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.BookService;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class SearchCommand extends BaseCommand {
    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.READER);
    }

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            HttpSession session = request.getSession(false);
            String searchText = (String) session.getAttribute("searchText");

            BookService bookService = ServiceProvider.getInstance().getBookService();

            int pageNumber;
            try {
                pageNumber = Integer.parseInt(request.getParameter("page")) - 1;
            } catch (NumberFormatException e) {
                pageNumber = 0;
            }

            int pageCount;
            try {
                int booksCount = bookService.getFindBooksCount(searchText);
                pageCount = booksCount / PageConstant.MAX_COUNT_OBJECTS_ON_PAGE + ((booksCount % PageConstant.MAX_COUNT_OBJECTS_ON_PAGE > 0)? 1 : 0);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            pageNumber = (pageNumber >= pageCount) ? pageCount : ((pageNumber < 0) ? 0 : pageNumber);

            List<Book> books;
            try {
                books = bookService.getFindBooks(searchText, pageNumber, pageCount);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }



        } else if ("POST".equals(request.getMethod())) {
            String searchText = request.getParameter("searchText");

            BookService bookService = ServiceProvider.getInstance().getBookService();

            int pageNumber = 0;

            int pageCount;
            try {
                int booksCount = bookService.getFindBooksCount(searchText);
                pageCount = booksCount / PageConstant.MAX_COUNT_OBJECTS_ON_PAGE + ((booksCount % PageConstant.MAX_COUNT_OBJECTS_ON_PAGE > 0)? 1 : 0);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            List<Book> books;
            try {
                books = bookService.getFindBooks(searchText, pageNumber, pageCount);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

        }
    }
}
