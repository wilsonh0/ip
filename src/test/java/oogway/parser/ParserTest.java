package oogway.parser;

import oogway.commands.*;
import oogway.storage.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ParserTest {
    private final Parser parser = new Parser(new TaskList());

    @Test
    void parseCommand_validTodoCommand_returnsAddTaskCommand() {
        Command command = parser.parseCommand("todo Read book");
        assertInstanceOf(AddTaskCommand.class, command);
    }

    @Test
    void parseCommand_validDeadlineCommand_returnsAddTaskCommand() {
        Command command = parser.parseCommand("deadline Submit report /by 2024-12-31 2359");
        assertInstanceOf(AddTaskCommand.class, command);
    }

    @Test
    void parseCommand_validEventCommand_returnsAddTaskCommand() {
        Command command = parser.parseCommand("event Project meeting /from 2024-12-31 1000 /to 1200");
        assertInstanceOf(AddTaskCommand.class, command);
    }

    @Test
    void parseCommand_listCommand_returnsListTasksCommand() {
        Command command = parser.parseCommand("list");
        assertInstanceOf(ListTasksCommand.class, command);
    }

    @Test
    void parseCommand_byeCommand_returnsExitCommand() {
        Command command = parser.parseCommand("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void parseCommand_unknownCommand_returnsIncorrectCommand() {
        Command command = parser.parseCommand("unknownCommand");
        assertInstanceOf(IncorrectCommand.class, command);
    }

    @Test
    void parseCommand_emptyInput_returnsIncorrectCommand() {
        Command command = parser.parseCommand("");
        assertInstanceOf(IncorrectCommand.class, command);
    }

    @Test
    void parseCommand_invalidTodoCommand_returnsIncorrectCommand() {
        Command command = parser.parseCommand("todo");
        assertInstanceOf(IncorrectCommand.class, command);
    }

    @Test
    void parseCommand_invalidDeadlineCommand_returnsIncorrectCommand() {
        Command command = parser.parseCommand("deadline Submit report");
        assertInstanceOf(IncorrectCommand.class, command);
    }

    @Test
    void parseCommand_invalidEventCommand_returnsIncorrectCommand() {
        Command command = parser.parseCommand("event Meeting /from 2024-12-31 1000");
        assertInstanceOf(IncorrectCommand.class, command);
    }

    @Test
    void parseCommand_markTask_returnsMarkTaskCommand() {
        Command command = parser.parseCommand("mark 1");
        assertInstanceOf(MarkTaskCommand.class, command);
    }

    @Test
    void parseCommand_unmarkTask_returnsMarkTaskCommand() {
        Command command = parser.parseCommand("unmark 1");
        assertInstanceOf(MarkTaskCommand.class, command);
    }

    @Test
    void parseCommand_deleteTask_returnsDeleteTaskCommand() {
        Command command = parser.parseCommand("delete 1");
        assertInstanceOf(DeleteTaskCommand.class, command);
    }

    @Test
    void parseCommand_invalidDeleteCommand_returnsIncorrectCommand() {
        Command command = parser.parseCommand("delete one");
        assertInstanceOf(IncorrectCommand.class, command);
    }

    @Test
    void parseCommand_extraSpaces_handlesCorrectly() {
        Command command = parser.parseCommand("  todo   Read book  ");
        assertInstanceOf(AddTaskCommand.class, command);
    }

    @Test
    void parseCommand_caseInsensitiveCommands_parsesCorrectly() {
        assertInstanceOf(AddTaskCommand.class, parser.parseCommand("TODO Read book"));
        assertInstanceOf(AddTaskCommand.class, parser.parseCommand("DeaDline Submit report /by 2024-12-31 2359"));
        assertInstanceOf(AddTaskCommand.class, parser.parseCommand("EVENT Meeting /from 2024-12-31 1000 /to 1200"));
    }
}
