package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.Book;
import by.it.training.library.controller.PageConstant;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.BookService;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BooksCommand extends BaseCommand {

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=books");
            request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/common/books.jsp");

            BookService bookService = ServiceProvider.getInstance().getBookService();

            int pageNumber;
            try {
                pageNumber = Integer.parseInt(request.getParameter("page")) - 1;
            } catch (NumberFormatException e) {
                pageNumber = 0;
            }

            int pageCount;
            try {
                int booksCount = bookService.getBooksCount();
                pageCount = booksCount / PageConstant.MAX_COUNT_BOOKS_ON_PAGE + ((booksCount % PageConstant.MAX_COUNT_BOOKS_ON_PAGE > 0)? 1 : 0);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            pageNumber = (pageNumber >= pageCount) ? pageCount : ((pageNumber < 0) ? 0 : pageNumber);

            List<Book> books;
            try {
                books = bookService.getBooks(pageNumber, pageCount);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            request.setAttribute("books", books);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("pageNumber", pageNumber + 1);
            request.setAttribute("pageCommand", "books");
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=books&page=" + (pageNumber + 1));
        }
    }
}

//<c:if test="${pageCount eq 1}">
//<div class="books-list">
//<c:out value="text"/>
//</div>
//</c:if>
