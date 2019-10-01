package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.Subscription;
import by.it.training.library.bean.User;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;
import by.it.training.library.service.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelReservedBookCommand extends BaseBookCommand {

    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("POST".equals(request.getMethod())) {
            SubscriptionService subscriptionService = ServiceProvider.getInstance().getSubscriptionService();

            try {
                HttpSession session = request.getSession(false);
                User user = (User) session.getAttribute(SessionAttributeName.USER);
                int subscriptionId = Integer.parseInt(request.getParameter("subscription"));

                Subscription subscription = subscriptionService.getSubscription(user.getId(), subscriptionId);
                subscriptionService.deleteSubscription(subscription);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
    }
}
