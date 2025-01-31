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

    /**
     * Prints the farewell message when the program exits.
     */
    public void exitMessage() {
        String message = "Farewell, young one. I hope to guide you again someday.\n"
                + "And never forget... Yesterday is history, Tomorrow is a mystery, but Today is a gift. "
                + "That is why it is called the present.";

        wrapMessage(message);
    }

    public void saveSuccessMessage() {
        wrapMessage("Tasks saved successfully");
    }

    public void saveErrorMessage(String errorMessage) {
        String message = String.format("Error while saving tasks: %s", errorMessage);

        wrapMessage(message);
    }

    public void loadSuccessMessage() {
        wrapMessage("Tasks loaded successfully");
    }

    public void loadErrorMessage(String errorMessage) {
        String message = String.format("Error while saving tasks: %s", errorMessage);

        wrapMessage(message);
    }
}
