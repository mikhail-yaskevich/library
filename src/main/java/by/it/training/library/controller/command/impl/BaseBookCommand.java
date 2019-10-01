package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.UserType;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class BaseBookCommand extends BaseCommand {

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.READER);
    }

    @Override
    public void doGo(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String parameter = request.getParameter("page");
            if (Objects.nonNull(parameter)) {
                response.sendRedirect(request.getContextPath() + "/main?command=subscriptions&page=" + parameter);
            } else {
                response.sendRedirect(request.getContextPath() + "/main?command=book&book=" + request.getParameter("book"));
            }
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
