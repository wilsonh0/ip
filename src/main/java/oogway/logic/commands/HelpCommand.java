package oogway.logic.commands;

/**
 * Displays the list of commands that the user can use.
 */
public class HelpCommand extends Command {

    private static final String MESSAGE_HELP =
            "=== GENERAL COMMANDS ===\n"
            + "help - Show this list of commands\n"
            + "bye - Exit the program\n\n"

            + "=== VIEWING TASKS ===\n"
            + "list - Display all tasks\n"
            + "find <keyword> - Search for tasks containing the keyword\n\n"

            + "=== ADDING TASKS ===\n"
            + "todo <desc> - Add a to-do task\n"
            + "deadline <desc> /by <yyyy-MM-dd HHmm> - Add a task with a deadline\n"
            + "event <desc> /from <yyyy-MM-dd HHmm> /to <HHmm> - Add an event with a time range\n\n"

            + "=== MODIFYING TASKS ===\n"
            + "mark <index> - Mark a task as completed\n"
            + "unmark <index> - Mark a task as incomplete\n"
            + "delete <index> - Remove a task from the list";

    @Override
    public CommandResult<Void> execute() {
        return new CommandResult<>(true, MESSAGE_HELP);
    }
}
