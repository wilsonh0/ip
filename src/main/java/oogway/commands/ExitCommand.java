package oogway.commands;

public class ExitCommand extends Command<Void> {

    private final String MESSAGE_EXIT = "Farewell, young one. I hope to guide you again someday.\n"
            + "And never forget... Yesterday is history, Tomorrow is a mystery, but Today is a gift. "
            + "That is why it is called the present.";

    @Override
    public CommandResult<Void> execute() {
        return new CommandResult<>(true, MESSAGE_EXIT);
    }

    public static boolean isExit(Command<?> command) {
        return command instanceof ExitCommand;
    }
}
