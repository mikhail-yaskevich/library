package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.UserType;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.Command;
import by.it.training.library.controller.command.CommandException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class LocaleCommand extends BaseCommand {
    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.GUEST, UserType.READER, UserType.ADMIN);
    }

    @Override
    public void doDefault(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String locale = request.getParameter("locale");
        response.addCookie(new Cookie("locale", locale) {
            {
                if (Objects.isNull(locale) || locale.isEmpty()) {
                    setMaxAge(0);
                }
            }
        });
    }

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        doDefault(request, response);
    }

    @Override
    public void doGo(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String lastRequest = request.getParameter(RequestParameterName.LAST_REQUEST);
            if (Objects.isNull(lastRequest) || lastRequest.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/main");
            } else {
                response.sendRedirect(request.getContextPath() + lastRequest);
            }
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
