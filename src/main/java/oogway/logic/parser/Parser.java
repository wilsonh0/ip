package oogway.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oogway.logic.commands.AddTaskCommand;
import oogway.logic.commands.Command;
import oogway.logic.commands.DeleteTaskCommand;
import oogway.logic.commands.ExitCommand;
import oogway.logic.commands.FindTaskCommand;
import oogway.logic.commands.IncorrectCommand;
import oogway.logic.commands.ListTasksCommand;
import oogway.logic.commands.MarkTaskCommand;
import oogway.storage.TaskList;

/**
 * Parses user input and returns the corresponding command object.
 */
public class Parser {
    private static final String DESC = "(?<description>[^/]+)";
    private static final String FROM = " /from (?<from>[^/]+)";
    private static final String TO = " /to (?<to>[^/]+)";
    private static final String BY = " /by (?<by>[^/]+)";

    private static final Pattern TODO_PATTERN = Pattern.compile("^" + DESC + "$");
    private static final Pattern DEADLINE_PATTERN = Pattern.compile("^" + DESC + BY + "$");
    private static final Pattern EVENT_PATTERN = Pattern.compile("^" + DESC + FROM + TO + "$");
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final String MESSAGE_INVALID_COMMAND = "Command not found!";
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    private static final String MESSAGE_INVALID_TASK_NUMBER = "ERROR: Task number must be an integer! \n%1$s";


    private final TaskList taskList;

    /**
     * Initializes a new parser with the specified task list.
     *
     * @param taskList The task list to be used by the parser.
     */
    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Parses the user input and returns the corresponding {@code Command} object.
     *
     * @param userInput The raw user input string.
     * @return A {@code Command} object corresponding to the input, or an {@code IncorrectCommand} if invalid.
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            return new IncorrectCommand(MESSAGE_INVALID_COMMAND);
        }

        String commandWord = matcher.group("commandWord").toLowerCase();
        String arguments = matcher.group("arguments").trim();

        return switch (commandWord) {
        case "bye" -> parseExit();
        case "todo" -> parseTodo(arguments);
        case "deadline" -> parseDeadline(arguments);
        case "event" -> parseEvent(arguments);
        case "list" -> parseList();
        case "find" -> parseFind(arguments);
        case "delete" -> parseDelete(arguments);
        case "mark", "unmark" -> parseMark(commandWord, arguments);
        default -> new IncorrectCommand(MESSAGE_INVALID_COMMAND);
        };
    }

    /**
     * Parses the user input for a todo task and returns the corresponding {@code AddTaskCommand} object.
     *
     * @param arguments The arguments for the todo task.
     * @return An {@code AddTaskCommand} object for adding a todo task, or an {@code IncorrectCommand} if invalid.
     */
    private Command parseTodo(String arguments) {
        final Matcher matcher = TODO_PATTERN.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Usage: todo <description>"));
        }
        String description = matcher.group("description");

        return AddTaskCommand.createTodo(taskList, description);
    }

    /**
     * Parses the user input for a deadline task and returns the corresponding {@code AddTaskCommand} object.
     *
     * @param arguments The arguments for the deadline task.
     * @return An {@code AddTaskCommand} object for adding a deadline task, or an {@code IncorrectCommand} if invalid.
     */
    private Command parseDeadline(String arguments) {
        final Matcher matcher = DEADLINE_PATTERN.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Usage: deadline <description> /by <yyyy-MM-dd HHmm>"));
        }
        String description = matcher.group("description");
        String by = matcher.group("by");

        return AddTaskCommand.createDeadline(taskList, description, by);
    }

    /**
     * Parses the user input for an event task and returns the corresponding {@code AddTaskCommand} object.
     *
     * @param arguments The arguments for the event task.
     * @return An {@code AddTaskCommand} object for adding an event task, or an {@code IncorrectCommand} if invalid.
     */
    private Command parseEvent(String arguments) {
        final Matcher matcher = EVENT_PATTERN.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Usage: event <description> /from <yyyy-MM-dd HHmm> /to <HHmm>"));
        }
        String description = matcher.group("description");
        String from = matcher.group("from");
        String to = matcher.group("to");

        return AddTaskCommand.createEvent(taskList, description, from, to);
    }

    /**
     * Parses the user input for marking or unmarking a task and returns the corresponding
     * {@code MarkTaskCommand} object.
     *
     * @param commandWord The command word for marking or unmarking a task.
     * @param arguments The arguments for marking or unmarking a task.
     * @return A {@code MarkTaskCommand} object for marking or unmarking a task, or {@code IncorrectCommand} if invalid.
     */
    private Command parseMark(String commandWord, String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments.trim()) - 1;
            return new MarkTaskCommand(taskList, taskIndex, commandWord.equals("mark"));
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_TASK_NUMBER, "Usage: mark <task number>"));
        }
    }

    /**
     * Parses the user input for deleting a task and returns the corresponding {@code DeleteTaskCommand} object.
     *
     * @param arguments The arguments for deleting a task.
     * @return A {@code DeleteTaskCommand} object for deleting a task, or an {@code IncorrectCommand} if invalid.
     */
    private Command parseDelete(String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments.trim()) - 1;
            return new DeleteTaskCommand(taskList, taskIndex);
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_TASK_NUMBER, "Usage: delete <task number>"));
        }
    }

    /**
     * Creates a {@code FindTaskCommand} using the given search query.
     *
     * @param arguments The search query for finding tasks.
     * @return A {@code FindTaskCommand} object for finding tasks, or an {@code IncorrectCommand} if invalid.
     */
    private FindTaskCommand parseFind(String arguments) {
        return new FindTaskCommand(taskList, arguments);
    }

    /**
     * Returns a {@code ListTasksCommand} to display all tasks in the task list.
     *
     * @return A {@code ListTasksCommand} object for listing tasks.
     */
    private ListTasksCommand parseList() {
        return new ListTasksCommand(taskList);
    }

    /**
     * Returns an {@code ExitCommand} to terminate the application.
     *
     * @return An {@code ExitCommand} object for exiting the application.
     */
    private ExitCommand parseExit() {
        return new ExitCommand();
    }
}
