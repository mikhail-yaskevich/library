package by.it.training.library.controller;

import by.it.training.library.controller.command.Command;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.controller.command.CommandName;
import by.it.training.library.controller.command.CommandProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = -2496256835484843776L;

    private static final Logger logger = LogManager.getLogger(Controller.class);

    private static final CommandProvider commandProvider = CommandProvider.getInstance();

    public Controller() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doCommand(req, resp);
    }

    private void doCommand(HttpServletRequest req, HttpServletResponse resp) {
        logger.trace(req.getMethod());
        String commandName = req.getParameter(RequestParameterName.COMMAND_NAME);
        logger.trace("command: " + commandName);

        req.setAttribute("locale", req.getLocale().toString());
        Cookie[] cookies = req.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if ("locale".equalsIgnoreCase(cookie.getName())) {
                    req.setAttribute("locale", cookie.getValue());
                    break;
                }
            }
        }

        Command command;
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute(SessionAttributeName.USER))) {
            command = getCommandWithoutSession(commandName);
        } else {
            command = commandProvider.getCommand(commandName);
        }

        try {
            command.execute(req, resp);
        } catch (CommandException e) {
            logger.error("controller", e);
        }
    }

    private Command getCommandWithoutSession(String commandName) {
        if (CommandName.REGISTRATION.name().equalsIgnoreCase(commandName)) {
            return commandProvider.getCommand(CommandName.REGISTRATION.name());
        } else if (CommandName.LOGIN.name().equalsIgnoreCase(commandName)) {
            return commandProvider.getCommand(CommandName.LOGIN.name());
        } else if (CommandName.BOOKS.name().equalsIgnoreCase(commandName)) {
            return commandProvider.getCommand(CommandName.BOOKS.name());
        } else if (CommandName.AUTHORS.name().equalsIgnoreCase(commandName)) {
            return commandProvider.getCommand(CommandName.AUTHORS.name());
        } else if (CommandName.ABOUT.name().equalsIgnoreCase(commandName)) {
            return commandProvider.getCommand(CommandName.ABOUT.name());
        } else if (CommandName.LOCALE.name().equalsIgnoreCase(commandName)) {
            return commandProvider.getCommand(CommandName.LOCALE.name());
        } else {
            return commandProvider.getCommand(CommandName.NO_SUCH_COMMAND.name());
        }
    }
}