package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.User;
import by.it.training.library.bean.UserType;
import by.it.training.library.controller.PageConstant;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;
import by.it.training.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ReadersCommand extends BaseCommand {

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.ADMIN);
    }

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=readers");
            request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/admin/readers.jsp");

            UserService userService = ServiceProvider.getInstance().getUserService();

            int pageNumber;
            try {
                pageNumber = Integer.parseInt(request.getParameter("page")) - 1;
            } catch (NumberFormatException e) {
                pageNumber = 0;
            }

            int pageCount;
            try {
                int usersCount = userService.getUsersCount();
                pageCount = usersCount / PageConstant.MAX_COUNT_OBJECTS_ON_PAGE + ((usersCount % PageConstant.MAX_COUNT_OBJECTS_ON_PAGE > 0)? 1 : 0);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            pageNumber = (pageNumber >= pageCount) ? pageCount : ((pageNumber < 0) ? 0 : pageNumber);

            List<User> users = null;

//            try {
//                users = userService.getUsers(pageNumber, pageCount);
//            } catch (ServiceException e) {
//                throw new CommandException(e);
//            }

            request.setAttribute("readers", users);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("pageNumber", pageNumber + 1);
            request.setAttribute("pageCommand", "readers");
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=readers&page=" + (pageNumber + 1));
        }
    }
}
