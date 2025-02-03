package oogway.commands;


public class IncorrectCommand extends Command {
    private final String message;

    public IncorrectCommand(String message) {
        this.message = message;
    }

    @Override
    public CommandResult<Void> execute() {
        return new CommandResult<>(false, message);
    }
}
