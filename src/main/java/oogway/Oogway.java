package oogway;

import java.io.IOException;
import java.util.Scanner;

import oogway.commands.Command;
import oogway.commands.CommandResult;
import oogway.commands.ExitCommand;
import oogway.parser.Parser;
import oogway.storage.Storage;
import oogway.storage.TaskList;
import oogway.ui.Ui;

/**
 * The main class for the Oogway application. It manages user interaction, command execution,
 * and task storage.
 */
public class Oogway {

    private Storage storage;
    private TaskList taskList;
    private final Ui ui;
    private final Parser parser;

    /**
     * Initializes the Oogway application with the specified file path for storage.
     *
     * @param filePath the file path where tasks are stored and retrieved
     */
    public Oogway(String filePath) {
        ui = new Ui();

        try {
            storage = new Storage(filePath);
            taskList = storage.loadFromFile();
        } catch (IOException e) {
            ui.wrapMessage(e.getMessage());
            taskList = new TaskList();
        }

        parser = new Parser(taskList);
    }

    /**
     * Starts the Oogway application by displaying an introduction message and
     * running the command loop.
     */
    public void run() {
        ui.introductionMessage();
        runCommandLoopUntilExitCommand();
    }

    /**
     * The entry point of the Oogway application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        new Oogway(null).run();
    }

    /**
     * Runs the command loop until an exit command is given by the user.
     * Reads user input, processes it, and executes the corresponding command.
     */
    private void runCommandLoopUntilExitCommand() {
        Scanner sc = new Scanner(System.in);
        Command<?> command;

        do {
            String userCommandText = sc.nextLine();
            command = parser.parseCommand(userCommandText);
            CommandResult<?> result = executeCommand(command);
            ui.wrapMessage(result.getMessage());
        } while (!ExitCommand.isExit(command));
    }

    /**
     * Executes the given command and saves the task list to storage.
     *
     * @param command the command to be executed
     * @return the result of executing the command
     * @throws RuntimeException if an exception occurs during command execution
     */
    private CommandResult<?> executeCommand(Command<?> command) {
        try {
            CommandResult<?> result = command.execute();
            storage.saveToFile(taskList);
            return result;
        } catch (Exception e) {
            ui.wrapMessage(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}