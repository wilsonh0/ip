package oogway.app;

import javafx.application.Application;
import oogway.ui.cli.CommandLineApp;

/**
 * Launcher class to start the application.
 */
public class Launcher {
    public static void main(String... args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("cli")) {
            new CommandLineApp("data/save.txt").run();
        } else {
            Application.launch(Main.class, args);
        }
    }
}
