package oogway.ui.cli;

/**
 * Represents the user interface for the Oogway application.
 */
public class Ui {

    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "Master Oogway";
    /**
     * Prints a message wrapped with horizontal lines.
     *
     * @param message The message to be wrapped and printed.
     */
    public void wrapMessage(String message) {
        System.out.printf("%s\n%s\n%s%n", LINE, message, LINE);
    }

    /**
     * Prints the introduction message when the program starts.
     */
    public void introductionMessage() {
        String message = "Greetings, young one. I am " + NAME + ".\n Enlighten me... What do you seek?";

        wrapMessage(message);
    }
}
