package oogway.commands;

public abstract class Command<T> {
    public abstract CommandResult<T> execute();
}
