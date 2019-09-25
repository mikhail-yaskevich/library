package by.it.training.library.controller.command.impl;

import by.it.training.library.bean.Subscription;
import by.it.training.library.bean.SubscriptionType;
import by.it.training.library.bean.User;
import by.it.training.library.bean.UserType;
import by.it.training.library.controller.PageConstant;
import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.SessionAttributeName;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.controller.command.SecureCommand;
import by.it.training.library.service.ServiceException;
import by.it.training.library.service.ServiceProvider;
import by.it.training.library.service.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class SubscriptionsCommand extends SecureCommand {
    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if ("GET".equals(request.getMethod())) {

            HttpSession session = request.getSession(false);
            User user = (User)session.getAttribute(SessionAttributeName.USER);
            UserType userType = UserType.valueOf((String) session.getAttribute(SessionAttributeName.USER_TYPE));

            switch (userType) {
                case READER: {
                    request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=subscriptions");
                    request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/reader/subscriptions.jsp");

                    SubscriptionService subscriptionService = ServiceProvider.getInstance().getSubscriptionService();

                    int pageNumber;
                    try {
                        pageNumber = Integer.parseInt(request.getParameter("page")) - 1;
                    } catch (NumberFormatException e) {
                        pageNumber = 0;
                    }

                    int pageCount;
                    try {
                        int subscriptionsCount = subscriptionService.getSubscriptionsCount(user.getId(), SubscriptionType.ALL);
                        pageCount = subscriptionsCount / PageConstant.MAX_COUNT_OBJECTS_ON_PAGE + ((subscriptionsCount % PageConstant.MAX_COUNT_OBJECTS_ON_PAGE > 0) ? 1 : 0);
                    } catch (ServiceException e) {
                        throw new CommandException(e);
                    }

                    pageNumber = (pageNumber >= pageCount) ? pageCount : ((pageNumber < 0) ? 0 : pageNumber);

                    List<Subscription> subscriptions;
                    try {
                        subscriptions = subscriptionService.getSubscriptions(user.getId(), SubscriptionType.ALL, pageNumber, pageCount);
                    } catch (ServiceException e) {
                        throw new CommandException(e);
                    }

                    request.setAttribute("subscriptions", subscriptions);
                    request.setAttribute("pageCount", pageCount);
                    request.setAttribute("pageNumber", pageNumber + 1);
                    request.setAttribute("pageCommand", "subscriptions");
                    request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=subscriptions&page=" + (pageNumber + 1));
                    break;
                }
                case ADMIN: {
                    request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=subscriptions");
                    request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/admin/subscriptions.jsp");
                    break;
                }
            }
        }
    }

    @Override
    public Set<UserType> getAvailableUserType() {
        return EnumSet.of(UserType.READER, UserType.ADMIN);
    }
}
