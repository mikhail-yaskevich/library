package by.it.training.library.controller.command.impl;

import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.Command;
import by.it.training.library.controller.command.CommandException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class LocaleCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String locale = request.getParameter("locale");
        response.addCookie(new Cookie("locale", locale) {
            {
                if (Objects.isNull(locale) || locale.isEmpty()) {
                    setMaxAge(0);
                }
            }
        });
        try {
            String lastRequest = request.getParameter(RequestParameterName.LAST_REQUEST);
            if (lastRequest.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/main");
            } else {
                response.sendRedirect(request.getContextPath() + lastRequest);
            }
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
