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

public class Oogway {
    private Storage storage;
    private TaskList taskList;
    private final Ui ui;
    private final Parser parser;

    public Oogway(String filePath) {
        ui = new Ui();

        try {
            storage = new Storage(filePath);
            taskList = storage.loadFromFile();
        } catch (IOException e) {
            ui.loadErrorMessage(e.getMessage());
            taskList = new TaskList();
        }

        parser = new Parser(taskList);
    }

    public void run() {
        ui.introductionMessage();
        runCommandLoopUntilExitCommand();
    }

    public static void main(String[] args) {
        new Oogway(null).run();
    }

    /** Reads the user command and executes it, until the user issues the exit command. */
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