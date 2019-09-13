package by.it.training.library.controller.command.impl;

import by.it.training.library.controller.command.Command;
import by.it.training.library.controller.command.CommandException;
import by.it.training.library.controller.command.CommandName;
import by.it.training.library.controller.command.CommandProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoSuchCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        System.out.println(">" + this.getClass().getSimpleName());
        Command command = CommandProvider.getInstance().getCommand(CommandName.GENERAL.name());
        command.execute(request, response);
    }
}
