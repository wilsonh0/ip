package oogway.parser;

import oogway.commands.*;
import oogway.storage.TaskList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Parser {
    private final TaskList taskList;

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Pattern EVENT_PATTERN = Pattern.compile("^(?<description>[^/]+) " +
            "/from (?<from>[^/]+) /to (?<to>[^/]+)$");
    private static final Pattern DEADLINE_PATTERN = Pattern.compile("^(?<description>[^/]+) /by (?<by>[^/]+)$");
    private static final Pattern TODO_PATTERN = Pattern.compile("^(?<description>[^/]+)$");

    private static final String MESSAGE_INVALID_COMMAND = "Command not found!";
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";


    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    public Command<?> parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            return new IncorrectCommand(MESSAGE_INVALID_COMMAND);
        }

        String commandWord = matcher.group("commandWord").toLowerCase();
        String arguments = matcher.group("arguments").trim();

        switch (commandWord) {
            case "todo":
                return parseTodo(arguments);
            case "deadline":
                return parseDeadline(arguments);
            case "event":
                return parseEvent(arguments);
            case "list":
                return new ListTasksCommand(taskList);
            case "bye":
                return new ExitCommand();
            case "mark", "unmark":
                return parseMarkTask(commandWord, arguments);
            case "delete":
                return parseDeleteTask(arguments);
            case "find":
                return new FindTaskCommand(taskList, arguments);
            default:
                return new IncorrectCommand(MESSAGE_INVALID_COMMAND);
        }
    }

    public Command<?> parseTodo(String arguments) {
        final Matcher matcher = TODO_PATTERN.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Usage: todo <description>"));
        }
        String description = matcher.group("description");

        return new AddTaskCommand(taskList, "todo", description);
    }

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

    public Command<?> parseMarkTask(String commandWord, String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments.trim()) - 1;
            return new MarkTaskCommand(taskList, taskIndex, commandWord.equals("mark"));
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Usage: mark <task number>"));
        }
    }

    public Command<?> parseDeleteTask(String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments.trim()) - 1;
            return new DeleteTaskCommand(taskList, taskIndex);
        } catch (NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Usage: delete <task number>"));
        }
    }
}
