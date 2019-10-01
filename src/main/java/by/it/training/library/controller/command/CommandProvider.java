package by.it.training.library.controller.command;

import by.it.training.library.controller.command.impl.*;

import java.util.EnumMap;
import java.util.Map;

public final class CommandProvider {

    private static final CommandProvider instance = new CommandProvider();

    public static CommandProvider getInstance() {
        return instance;
    }

    private Map<CommandName, Command> commands = new EnumMap<>(CommandName.class);

    private CommandProvider() {
        GeneralCommand command = new GeneralCommand();
        commands.put(CommandName.NO_SUCH_COMMAND, command);
        commands.put(CommandName.GENERAL, command);
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.LOCALE, new LocaleCommand());
        commands.put(CommandName.BOOKS, new BooksCommand());
        commands.put(CommandName.AUTHORS, new AuthorsCommand());
        commands.put(CommandName.ABOUT, new AboutCommand());
        commands.put(CommandName.SUBSCRIPTIONS, new SubscriptionsCommand());
        commands.put(CommandName.READERS, new ReadersCommand());
        commands.put(CommandName.BOOK, new BookCommand());
        commands.put(CommandName.AUTHOR, new AuthorCommand());
        commands.put(CommandName.RESERVE, new ReserveBookCommand());
        commands.put(CommandName.TAKE, new TakeBookCommand());
        commands.put(CommandName.BRING, new BringBookCommand());
        commands.put(CommandName.CANCEL, new CancelReservedBookCommand());

        commands.put(CommandName.UPLOAD, new UploadCommand());
    }

    public Command getCommand(String commandName) {
        try {
            return commands.get(CommandName.valueOf(commandName.toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            return commands.get(CommandName.NO_SUCH_COMMAND);
        }
    }
}
