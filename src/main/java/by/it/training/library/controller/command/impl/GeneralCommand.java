package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.UserType;
import by.it.training.library.controller.PageName;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.BookService;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;
import java.util.Set;

public class GeneralCommand extends BaseCommand {

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.GUEST, UserType.READER, UserType.ADMIN);
    }

    @Override
    public void doDefault(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, PageName.GET_GENERAL);
            request.setAttribute(RequestParameterName.PAGE, PageName.PAGE_GENERAL);

            BookService bookService = ServiceProvider.getInstance().getBookService();
            try {
                request.setAttribute(RequestParameterName.BOOKS, bookService.getLastReadBooks());
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
    }

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        doDefault(request, response);
    }
}
