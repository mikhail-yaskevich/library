package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.User;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;
import by.it.training.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginCommand extends BaseCommand {

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=login");
            request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/common/login.jsp");
            request.setAttribute("loginPage", "true");
        } else if ("POST".equals(request.getMethod())) {
            UserService userService = ServiceProvider.getInstance().getUserService();
            try {
                User user = userService.authorization(
                        request.getParameter(RequestParameterName.LOGIN), request.getParameter(RequestParameterName.PASSWORD));

                if (Objects.nonNull(user)) {
                    request.getSession().setAttribute(SessionAttributeName.USER, user);
                    request.getSession().setAttribute(SessionAttributeName.USER_TYPE, user.getUserType().name());
                } else {
                    request.setAttribute("loginPage", "true");
                    String[] strings = ((String) request.getAttribute("locale")).split("_");//ru_RU
                    ResourceBundle bundle = ResourceBundle.getBundle("language.title", new Locale(strings[0], strings[1]));
                    request.setAttribute("loginError", bundle.getString("login.error"));
                    request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/common/login.jsp");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
    }
}