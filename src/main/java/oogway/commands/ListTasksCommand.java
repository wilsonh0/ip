package oogway.commands;

import oogway.storage.TaskList;
import oogway.tasks.Task;

public class ListTasksCommand extends Command<Task> {
    private final TaskList taskList;

    public ListTasksCommand(TaskList taskList) {
        this.taskList = taskList;
    }

    @Override
    public CommandResult<Task> execute() {
        if (taskList.isEmpty()) {
            return new CommandResult<>(false, "There are no tasks in your list");
        } else {
            StringBuilder message = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < taskList.getTaskCount(); i++) {
                message.append(((i + 1))).append(".").append(taskList.getTask(i)).append("\n");
            }
            return new CommandResult<>(true, message.toString());
        }
    }
}
