package oogway.logic.commands;

/**
 * Represents a command that can be executed.
 *
 * Inspired by the command pattern in AddressBook Level 2:
 * https://github.com/se-edu/addressbook-level2, but implemented differently.
 */
public abstract class Command {
    public abstract CommandResult<?> execute();
}
