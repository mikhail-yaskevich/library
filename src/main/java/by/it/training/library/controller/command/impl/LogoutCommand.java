package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.UserType;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.Command;
import by.it.training.library.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class LogoutCommand extends BaseCommand {

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.READER, UserType.ADMIN);
    }

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);
        if (Objects.nonNull(session.getAttribute(SessionAttributeName.USER))) {
            session.invalidate();
        }
    }

    @Override
    public void doGo(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            response.sendRedirect(request.getContextPath() + "/main");
        } catch (IOException e) {
            throw new CommandException(e);
        }
    }
}
