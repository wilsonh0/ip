package oogway.logic.commands;

/**
 * Represents a command that is not recognized by the parser.
 */
public class IncorrectCommand extends Command {
    private final String message;

    /**
     * Initializes a new IncorrectCommand with the specified message.
     *
     * @param message The message to display to the user.
     */
    public IncorrectCommand(String message) {
        this.message = message;
    }

    @Override
    public CommandResult<Void> execute() {
        return new CommandResult<>(false, message);
    }
}
