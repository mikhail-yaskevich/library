package by.it.training.library.controller.command;

import by.it.training.library.controller.command.impl.*;
import by.it.training.library.controller.command.impl.AboutCommand;

import java.util.EnumMap;
import java.util.Map;

public final class CommandProvider {

    private static final CommandProvider instance = new CommandProvider();

    public static CommandProvider getInstance() {
        return instance;
    }

    private Map<CommandName, Command> commands = new EnumMap<>(CommandName.class);

    private CommandProvider() {
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.GENERAL, new GeneralCommand());
        commands.put(CommandName.BOOKS, new BooksCommand());
        commands.put(CommandName.AUTHORS, new AuthorsCommand());
        commands.put(CommandName.ABOUT, new AboutCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.LOCALE, new LocaleCommand());
        commands.put(CommandName.SUBSCRIPTIONS, new SubscriptionsCommand());
    }

    public Command getCommand(String commandName) {
        try {
            return commands.get(CommandName.valueOf(commandName.toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            return commands.get(CommandName.NO_SUCH_COMMAND);
        }
    }
}
