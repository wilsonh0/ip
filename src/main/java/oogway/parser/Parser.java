package oogway.parser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import oogway.commands.*;
import oogway.storage.TaskList;


/**
 * Parses user input and returns the corresponding command object.
 */
public class Parser {
    private final TaskList taskList;

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Pattern EVENT_PATTERN = Pattern.compile("^(?<description>[^/]+) " +
            "/from (?<from>[^/]+) /to (?<to>[^/]+)$");
    private static final Pattern DEADLINE_PATTERN = Pattern.compile("^(?<description>[^/]+) /by (?<by>[^/]+)$");
    private static final Pattern TODO_PATTERN = Pattern.compile("^(?<description>[^/]+)$");

    private static final String MESSAGE_INVALID_COMMAND = "Command not found!";
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

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
    public Command<?> parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            return new IncorrectCommand(MESSAGE_INVALID_COMMAND);
        }

        String commandWord = matcher.group("commandWord").toLowerCase();
        String arguments = matcher.group("arguments").trim();

        return switch (commandWord) {
            case "todo" -> parseTodo(arguments);
            case "deadline" -> parseDeadline(arguments);
            case "event" -> parseEvent(arguments);
            case "list" -> new ListTasksCommand(taskList);
            case "bye" -> new ExitCommand();
            case "mark", "unmark" -> parseMarkTask(commandWord, arguments);
            case "find" -> new FindTaskCommand(taskList, arguments);
            case "delete" -> parseDeleteTask(arguments);
            default -> new IncorrectCommand(MESSAGE_INVALID_COMMAND);
        };
    }

    /**
     * Parses the user input for a todo task and returns the corresponding {@code AddTaskCommand} object.
     *
     * @param arguments The arguments for the todo task.
     * @return An {@code AddTaskCommand} object for adding a todo task, or an {@code IncorrectCommand} if invalid.
     */
    public Command<?> parseTodo(String arguments) {
        final Matcher matcher = TODO_PATTERN.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Usage: todo <description>"));
        }
        String description = matcher.group("description");

        return new AddTaskCommand(taskList, "todo", description);
    }

    /**
     * Parses the user input for a deadline task and returns the corresponding {@code AddTaskCommand} object.
     *
     * @param arguments The arguments for the deadline task.
     * @return An {@code AddTaskCommand} object for adding a deadline task, or an {@code IncorrectCommand} if invalid.
     */
    public Command<?> parseDeadline(String arguments) {
        final Matcher matcher = DEADLINE_PATTERN.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Usage: deadline <description> /by <yyyy-MM-dd HHmm>"));
        }
        String description = matcher.group("description");
        String by = matcher.group("by");

        return new AddTaskCommand(taskList, "deadline", description, by);
    }

    /**
     * Parses the user input for an event task and returns the corresponding {@code AddTaskCommand} object.
     *
     * @param arguments The arguments for the event task.
     * @return An {@code AddTaskCommand} object for adding an event task, or an {@code IncorrectCommand} if invalid.
     */
    public Command<?> parseEvent(String arguments) {
        final Matcher matcher = EVENT_PATTERN.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Usage: event <description> /from <yyyy-MM-dd HHmm> /to <HHmm>"));
        }
        String description = matcher.group("description");
        String from = matcher.group("from");
        String to = matcher.group("to");

        return new AddTaskCommand(taskList, "event", description, from, to);
    }

    /**
     * Parses the user input for marking or unmarking a task and returns the corresponding {@code MarkTaskCommand} object.
     *
     * @param commandWord The command word for marking or unmarking a task.
     * @param arguments The arguments for marking or unmarking a task.
     * @return A {@code MarkTaskCommand} object for marking or unmarking a task, or an {@code IncorrectCommand} if invalid.
     */
    public Command<?> parseMarkTask(String commandWord, String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments.trim()) - 1;
            return new MarkTaskCommand(taskList, taskIndex, commandWord.equals("mark"));
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Usage: mark <task number>"));
        }
    }

    /**
     * Parses the user input for deleting a task and returns the corresponding {@code DeleteTaskCommand} object.
     *
     * @param arguments The arguments for deleting a task.
     * @return A {@code DeleteTaskCommand} object for deleting a task, or an {@code IncorrectCommand} if invalid.
     */
    public Command<?> parseDeleteTask(String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments.trim()) - 1;
            return new DeleteTaskCommand(taskList, taskIndex);
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Usage: delete <task number>"));
        }
    }
}
