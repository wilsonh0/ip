package oogway.logic.commands;

/**
 * Exits the program.
 */
public class ExitCommand extends Command {

    private static final String MESSAGE_EXIT = "Farewell, young one. I hope to guide you again someday.\n"
            + "And never forget... Yesterday is history, Tomorrow is a mystery, but Today is a gift. "
            + "That is why it is called the present.";

    @Override
    public CommandResult<Void> execute() {
        return new CommandResult<>(true, MESSAGE_EXIT);
    }

    /**
     * Checks if the command is an exit command.
     *
     * @param command The command to check.
     * @return True if the command is an exit command, false otherwise.
     */
    public static boolean isExit(Command command) {
        return command instanceof ExitCommand;
    }
}
