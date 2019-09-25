package by.it.training.library.controller;

import by.it.training.library.controller.command.Command;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.controller.command.CommandName;
import by.it.training.library.controller.command.CommandProvider;
import by.it.training.library.dao.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Locale;
import java.util.Objects;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = -2496256835484843776L;

    private static final Logger logger = LogManager.getLogger(Controller.class);

    private static final CommandProvider commandProvider = CommandProvider.getInstance();

    public Controller() {
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().dispose();
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

        // код для тестирования загрузки файлов
        if ("upload".equals(req.getParameter("command"))) {
            System.out.println("description " + req.getParameter("description"));
            System.out.println("file " + req.getParameter("file"));
            try {
                Part part = req.getPart("file"); // Retrieves <input type="file" name="file">
                System.out.println("getSubmittedFileName()" + part.getSubmittedFileName());
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

                System.out.println("fileName " + fileName);
                try (InputStream input = part.getInputStream()) {
                    System.out.println("input " + input.available());
                    String upload = getServletConfig().getInitParameter("upload");
                    System.out.println("upload " + upload);
                    File tempFile = File.createTempFile("xxx_", "_zzz", new File(upload));
                    System.out.println("tempFile: " + tempFile);
                    Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException | ServletException e) {
                e.printStackTrace();
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
        } else if (CommandName.BOOK.name().equalsIgnoreCase(commandName)) {
            return commandProvider.getCommand(CommandName.BOOK.name());
        } else if (CommandName.AUTHOR.name().equalsIgnoreCase(commandName)) {
            return commandProvider.getCommand(CommandName.AUTHOR.name());
        } else {
            return commandProvider.getCommand(CommandName.NO_SUCH_COMMAND.name());
        }
    }
}