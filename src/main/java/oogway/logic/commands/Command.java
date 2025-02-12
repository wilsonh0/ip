package oogway.logic.commands;

/**
 * Represents a command that can be executed.
 */
public abstract class Command {
    public abstract CommandResult<?> execute();
}
