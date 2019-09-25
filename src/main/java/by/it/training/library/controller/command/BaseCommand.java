package by.it.training.library.controller.command;

import by.it.training.library.bean.UserType;
import by.it.training.library.controller.SessionAttributeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class BaseCommand implements Command {

    private static final Logger logger = LogManager.getLogger(BaseCommand.class);

    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.GUEST);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            doPrepare(request, response);

            HttpSession session = request.getSession(false);
            try {
                if (Objects.isNull(session)) {
                    logger.trace("guest");
                    if (getAvailableUserType().contains(UserType.GUEST)) {
                        logger.trace("guest: " + "default");
                        doDefault(request, response);
                    }
                } else {
                    logger.trace("user or guest");
                    logger.trace("user or guest attribute: " + session.getAttribute(SessionAttributeName.USER));
                    logger.trace("user or guest attribute: " + session.getAttribute(SessionAttributeName.USER_TYPE));
                    if (Objects.isNull(session.getAttribute(SessionAttributeName.USER)) ||
                            Objects.isNull(session.getAttribute(SessionAttributeName.USER_TYPE))) {
                        logger.trace("user or guest: " + "default");
                        doDefault(request, response);
                    } else {
                        logger.trace("user ");
                        String attribute = ((String) session.getAttribute(SessionAttributeName.USER_TYPE)).toUpperCase();
                        if (getAvailableUserType().contains(UserType.valueOf(attribute))) {
                            logger.trace("user: " + "execute");
                            doExecute(request, response);
                        }
                    }
                }
            } finally {
                logger.trace("go");
                doGo(request, response);
            }
        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            throw new CommandException(e);
        }
    }

    private void doPrepare(HttpServletRequest request, HttpServletResponse response) {
        logger.trace("class: " + this.getClass().getSimpleName());
    }

    public void doDefault(HttpServletRequest request, HttpServletResponse response) throws CommandException {

    }

    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

    }

    public void doGo(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            request.getRequestDispatcher("WEB-INF/jsp/main.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }
    }
}
