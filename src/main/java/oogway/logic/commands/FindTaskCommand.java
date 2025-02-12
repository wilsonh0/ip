package oogway.logic.commands;

import java.util.List;

import oogway.storage.TaskList;
import oogway.tasks.Task;

/**
 * Finds tasks in the task list that match a given keyword.
 */
public class FindTaskCommand extends Command {
    private static final String MESSAGE_TASK_NOT_FOUND = "No tasks found!";

    private final TaskList taskList;
    private final String keyword;

    /**
     * Initializes a new FindTaskCommand with the specified task list and keyword.
     *
     * @param taskList The task list to search for tasks.
     * @param keyword The keyword to search for in tasks.
     */
    public FindTaskCommand(TaskList taskList, String keyword) {
        this.taskList = taskList;
        this.keyword = keyword;
    }

    /**
     * Executes the command to find tasks that match the keyword.
     *
     * @return A CommandResult object with the result of the command execution.
     */
    public CommandResult<List<Task>> execute() {
        List<Task> foundTasks = taskList.findTasks(keyword);

        if (foundTasks.isEmpty()) {
            return new CommandResult<>(false, MESSAGE_TASK_NOT_FOUND);
        }

        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < foundTasks.size(); i++) {
            message.append(((i + 1))).append(".").append(foundTasks.get(i)).append("\n");
        }

        return new CommandResult<>(true, message.toString(), foundTasks);
    }
}
