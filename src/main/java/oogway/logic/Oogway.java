
package oogway.logic;

import java.io.IOException;

import oogway.logic.commands.Command;
import oogway.logic.commands.CommandResult;
import oogway.logic.parser.Parser;
import oogway.storage.Storage;
import oogway.storage.TaskList;

/**
 * The main class for the Oogway application. It manages user interaction, command execution,
 * and task storage.
 */
public class Oogway {

    private Storage storage;
    private TaskList taskList;
    private final Parser parser;

    /**
     * Initializes the Oogway application with the specified file path for storage.
     *
     * @param filePath the file path where tasks are stored and retrieved
     */
    public Oogway(String filePath) {
        try {
            storage = new Storage(filePath);
            taskList = storage.loadFromFile();
        } catch (IOException e) {
            taskList = new TaskList();
        }
        parser = new Parser(taskList);
    }

    /**
     * Executes the given command and saves the task list to storage.
     *
     * @param command the command to be executed
     * @return the result of executing the command
     * @throws RuntimeException if an exception occurs during command execution
     */
    public CommandResult<?> executeCommand(Command command) {
        assert command != null : "Command should not be null";

        try {
            CommandResult<?> result = command.execute();
            storage.saveToFile(taskList);
            return result;
        } catch (IOException e) {
            return new CommandResult<>(false, "Error saving tasks to file: " + e.getMessage());
        } catch (Exception e) {
            return new CommandResult<>(false, "Error executing command: " + e.getMessage());
        }
    }

    /**
     * Parses the given user command text and returns the corresponding command.
     *
     * @param userCommandText the user command text to be parsed
     * @return the command corresponding to the user command text
     */
    @SuppressWarnings("checkstyle:Regexp")
    public Command parseCommand(String userCommandText) {
        assert userCommandText != null : "User command text should not be null";

        Command command = parser.parseCommand(userCommandText);
        assert command != null : "Command should not be null";

        return command;
    }
}