package oogway.logic.commands;

import java.util.Optional;

/**
 * Represents the result of executing a command.
 *
 * @param <T> The type of data returned by the command.
 */
public class CommandResult<T> {

    private final boolean isSuccess;
    private final String message;
    private final T data;

    /**
     * Initializes a new CommandResult with the specified success status, message, and data.
     *
     * @param isSuccess The success status of the command.
     * @param message The message to display to the user.
     * @param data The data returned by the command.
     */
    public CommandResult(boolean isSuccess, String message, T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    /**
     * Initializes a new CommandResult with the specified success status and message.
     *
     * @param isSuccess The success status of the command.
     * @param message The message to display to the user.
     */
    public CommandResult(boolean isSuccess, String message) {
        this(isSuccess, message, null);
    }

    /**
     * Gets the success status of the command.
     *
     * @return The success status of the command.
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * Gets the message to display to the user.
     *
     * @return The message to display to the user.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the data returned by the command.
     *
     * @return An Optional containing the data returned by the command.
     */
    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }
}
