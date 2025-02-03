package oogway.commands;

import oogway.storage.TaskList;
import oogway.tasks.Task;

import java.util.List;

public class FindTaskCommand extends Command {
    private final TaskList taskList;
    private final String keyword;

    private final String MESSAGE_TASK_NOT_FOUND = "No tasks found!";

    public FindTaskCommand(TaskList taskList, String keyword) {
        this.taskList = taskList;
        this.keyword = keyword;
    }

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
