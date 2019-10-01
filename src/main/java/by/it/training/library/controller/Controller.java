package by.it.training.library.controller;

import by.it.training.library.controller.command.CommandProvider;
import by.it.training.library.dao.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        doCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        doCommand(req, resp);
    }

    private void doCommand(HttpServletRequest req, HttpServletResponse resp) {
        logger.info(req.getMethod());
        logger.info("command: " + req.getParameter(RequestParameterName.COMMAND_NAME));

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

        try {
            commandProvider.getCommand(req.getParameter(RequestParameterName.COMMAND_NAME)).execute(req, resp);
        } catch (Exception e) {
            logger.error("controller: ", e);
        }
    }
}