package oogway.logic.commands;

import java.util.Optional;

public class CommandResult<T> {

    private final boolean isSuccess;
    private final String message;
    private final T data;

    public CommandResult(boolean isSuccess, String message, T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public CommandResult(boolean isSuccess, String message) {
        this(isSuccess, message, null);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }
}
