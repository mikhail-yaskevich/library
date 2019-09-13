package by.it.training.library.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseCommand implements Command {

    private static final Logger logger = LogManager.getLogger(BaseCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        doBeforeExecute(request, response);
        doExecute(request, response);
        doAfterExecute(request, response);
    }

    public void doBeforeExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.info("class: " + this.getClass().getSimpleName());
    }

    public abstract void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    public void doAfterExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            request.getRequestDispatcher("WEB-INF/jsp/main.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }

    }
}
