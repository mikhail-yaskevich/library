package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.User;
import by.it.training.library.bean.UserType;
import by.it.training.library.bean.impl.UserBean;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.Command;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;
import by.it.training.library.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationCommand extends BaseCommand {

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=registration");
            request.setAttribute("registrationPage", "true");
        } else if ("POST".equals(request.getMethod())) {
            UserService userService = ServiceProvider.getInstance().getUserService();

            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String passwordyet = request.getParameter("passwordyet");
            String email = request.getParameter("email");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");

            String[] strings = ((String) request.getAttribute("locale")).split("_");//ru_RU
            ResourceBundle bundle = ResourceBundle.getBundle("language.title", new Locale(strings[0], strings[1]));

            if (login.isEmpty() || password.isEmpty() || passwordyet.isEmpty() || email.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
                request.setAttribute("registrationPage", "true");
                request.setAttribute("registrationError", bundle.getString("registration.error1"));
                request.setAttribute("oldLogin", login);
                request.setAttribute("oldEmail", email);
                request.setAttribute("oldFirstname", firstname);
                request.setAttribute("oldLastname", lastname);
            } else if (!password.equals(passwordyet)) {
                request.setAttribute("registrationPage", "true");
                request.setAttribute("registrationError", bundle.getString("registration.error2"));
                request.setAttribute("oldLogin", login);
                request.setAttribute("oldEmail", email);
                request.setAttribute("oldFirstname", firstname);
                request.setAttribute("oldLastname", lastname);
            } else {
                User userParams = new UserBean(0, login, password, email, firstname, lastname, null, null, null, UserType.READER);
                try {
                    if (userService.registrationCheck(userParams)) {
                        userService.registration(userParams);
                    } else {
                        request.setAttribute("registrationPage", "true");
                        request.setAttribute("registrationError", bundle.getString("registration.error3"));
                        request.setAttribute("oldLogin", login);
                        request.setAttribute("oldEmail", email);
                        request.setAttribute("oldFirstname", firstname);
                        request.setAttribute("oldLastname", lastname);
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }

                try {
                    User user = userService.authorization(userParams.getLogin(), userParams.getPassword());
                    if (Objects.nonNull(user)) {
                        request.getSession().setAttribute(SessionAttributeName.USER, user);
                    } else {
                        request.setAttribute("loginPage", "true");
                        request.setAttribute("loginError", bundle.getString("registration.error4"));
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            }
        }
        request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/common/registration.jsp");
    }
}
