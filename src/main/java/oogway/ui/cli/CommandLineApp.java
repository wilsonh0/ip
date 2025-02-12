
package oogway.ui.cli;

import java.util.Scanner;

import oogway.logic.Oogway;
import oogway.logic.commands.Command;
import oogway.logic.commands.CommandResult;
import oogway.logic.commands.ExitCommand;

/**
 * Represents the command line interface for the Oogway application.
 */
public class CommandLineApp {

    private final Oogway oogway;
    private final Ui ui;

    /**
     * Constructs a new command line interface for the Oogway application.
     *
     * @param filePath The file path to the data file.
     */
    public CommandLineApp(String filePath) {
        oogway = new Oogway(filePath);
        ui = new Ui();
    }

    /**
     * Runs the command line interface.
     */
    public void run() {
        ui.introductionMessage();
        runCommandLoopUntilExitCommand();
    }

    /**
     * Runs the command loop until the exit command is given.
     */
    private void runCommandLoopUntilExitCommand() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                String userCommandText = sc.nextLine();
                Command command = oogway.parseCommand(userCommandText);
                CommandResult<?> result = oogway.executeCommand(command);

                ui.wrapMessage(result.getMessage());

                if (ExitCommand.isExit(command)) { // Directly check exit condition here
                    break; // Exit the loop
                }
            } catch (Exception e) {
                ui.wrapMessage("Error: " + e.getMessage()); // Prevents the app from crashing
            }
        }

        sc.close();
    }
}
