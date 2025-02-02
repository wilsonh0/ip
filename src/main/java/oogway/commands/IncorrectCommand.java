package oogway.commands;

import oogway.tasks.Task;

public class IncorrectCommand extends Command<Task> {
    private final String message;

    public IncorrectCommand(String message) {
        this.message = message;
    }

    @Override
    public CommandResult<Task> execute() {
        return new CommandResult<>(false, message);
    }
}
