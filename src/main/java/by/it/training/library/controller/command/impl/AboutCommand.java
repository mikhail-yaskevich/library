package by.it.training.library.controller.command.impl;

import by.it.training.library.controller.RequestParameterName;
import by.it.training.library.controller.command.BaseCommand;
import by.it.training.library.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AboutCommand extends BaseCommand {
    @Override
    public void doExecute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.setAttribute(RequestParameterName.LAST_REQUEST, "/main?command=about");
        request.setAttribute(RequestParameterName.PAGE, "/WEB-INF/jsp/common/about.jsp");
    }
}
