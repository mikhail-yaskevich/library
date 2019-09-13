package by.it.training.library.controller.command.impl;

import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.BookService;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneralCommand extends BaseCommand {

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=general");
            request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/common/general.jsp");

            BookService bookService = ServiceProvider.getInstance().getBookService();
            try {
                request.setAttribute("books", bookService.getLastReadBooks());
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
    }
}
