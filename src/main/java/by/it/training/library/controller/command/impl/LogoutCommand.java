package by.it.training.library.controller.command.impl;

import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.Command;
import by.it.training.library.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class LogoutCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {
            HttpSession session = request.getSession();
            if (Objects.nonNull(session.getAttribute(SessionAttributeName.USER))) {
                session.invalidate();
                try {
                    response.sendRedirect(request.getContextPath() + "/main");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
