package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.Subscription;
import by.it.training.library.bean.User;
import by.it.training.library.bean.UserType;
import by.it.training.library.bean.impl.SubscriptionBean;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;
import by.it.training.library.service.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class ReserveBookCommand extends BaseCommand {

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.READER);
    }

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("POST".equals(request.getMethod())) {
            SubscriptionService subscriptionService = ServiceProvider.getInstance().getSubscriptionService();

            try {
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute(SessionAttributeName.USER);

                String parameter = request.getParameter("subscription");
                if (Objects.isNull(parameter)){
                    int bookId = Integer.parseInt(request.getParameter("book"));
                    subscriptionService.addSubscription(
                            new SubscriptionBean(-1, user.getId(), bookId, null, null, Timestamp.from(Instant.now()), "", 0));
                } else {
                    int subscriptionId = Integer.parseInt(parameter);

                    Subscription subscription = subscriptionService.getSubscription(user.getId(), subscriptionId);
                    subscription.setReserved(Timestamp.from(Instant.now()));
                    subscriptionService.updateSubscription(subscription);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }

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
