package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.User;
import by.it.training.library.bean.UserType;
import by.it.training.library.controller.PageName;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;
import by.it.training.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class LoginCommand extends BaseCommand {

    @Override
    public void doDefault(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, PageName.GET_LOGIN);
            request.setAttribute(RequestParameterName.PAGE, PageName.PAGE_LOGIN);
            request.setAttribute(RequestParameterName.LOGIN_PAGE, "true");
        } else if ("POST".equals(request.getMethod())) {
            UserService userService = ServiceProvider.getInstance().getUserService();
            try {
                User user = userService.authorization(
                        request.getParameter(RequestParameterName.LOGIN), request.getParameter(RequestParameterName.PASSWORD));

                if (Objects.nonNull(user)) {
                    request.getSession().setAttribute(SessionAttributeName.USER, user);
                    request.getSession().setAttribute(SessionAttributeName.USER_TYPE, user.getUserType().name());
                } else {
                    request.setAttribute(RequestParameterName.PAGE, PageName.PAGE_LOGIN);
                    request.setAttribute(RequestParameterName.LOGIN_PAGE, "true");
                    String[] strings = ((String) request.getAttribute("locale")).split("_");//ru_RU
                    ResourceBundle bundle = ResourceBundle.getBundle("language.title", new Locale(strings[0], strings[1]));
                    request.setAttribute("loginError", bundle.getString("login.error"));
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
    }

    @Override
    public void doGo(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User) request.getSession().getAttribute(SessionAttributeName.USER);
        if (Objects.nonNull(user)) {
            try {
                response.sendRedirect(request.getContextPath() + "/main");
            } catch (IOException e) {
                throw new CommandException(e);
            }
        } else {
            super.doGo(request, response);
        }
    }
}