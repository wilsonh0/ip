package oogway.logic.commands;

import oogway.storage.TaskList;
import oogway.tasks.Task;

public class DeleteTaskCommand extends Command {
    private final TaskList taskList;
    private final int taskIndex;

    private final String MESSAGE_DELETED = "Alright, I have removed the task:\n" + "%s"
            + "\nNow you have %s tasks in the list.";

    public DeleteTaskCommand(TaskList taskList, int taskIndex) {
        this.taskList = taskList;
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandResult<Task> execute() {
        if (taskIndex < 0 || taskIndex >= taskList.getTaskCount()) {
            return new CommandResult<>(false, "Invalid task number");
        }

        Task task = taskList.deleteTask(taskIndex);

        String message = String.format(MESSAGE_DELETED, task, taskList.getTaskCount());
        return new CommandResult<>(true, message);
    }
}
