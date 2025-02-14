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
            + "todo <description> - Add a to-do task\n"
            + "deadline <description> /by <date> - Add a task with a deadline\n"
            + "event <description> /from <start> /to <end> - Add an event with a time range\n\n"

            + "=== MODIFYING TASKS ===\n"
            + "done <task number> - Mark a task as completed\n"
            + "delete <task number> - Remove a task from the list";

    @Override
    public CommandResult<Void> execute() {
        return new CommandResult<>(true, MESSAGE_HELP);
    }
}
