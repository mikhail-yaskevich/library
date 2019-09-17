package by.it.training.library.controller.command;

import by.it.training.library.bean.UserType;
import by.it.training.library.controller.SessionAttributeName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public abstract class SecureCommand extends BaseCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);
        try {
            if (Objects.isNull(session) ||
                    Objects.isNull(session.getAttribute(SessionAttributeName.USER)) ||
                    Objects.isNull(session.getAttribute(SessionAttributeName.USER_TYPE))) {
                return;
            }
            String attribute = ((String) session.getAttribute(SessionAttributeName.USER_TYPE)).toUpperCase();
            if (!getUserType().equals(UserType.valueOf(attribute))) {
                return;
            }
            doBeforeExecute(request, response);
            doExecute(request, response);
        } finally {
            doAfterExecute(request, response);
        }
    }

    public abstract UserType getUserType();
}
