package oogway.logic.commands;

import oogway.storage.TaskList;
import oogway.tasks.Task;

/**
 * Marks a task as done or undone in the task list.
 */
public class MarkTaskCommand extends Command {
    private static final String MESSAGE_MARKED = "Ah, young one, it brings me great joy to see progress. "
            + "I have marked this task as complete for you:\n";
    private static final String MESSAGE_UNMARKED = "Patience, young one. I have returned this task to "
            + "its unfinished state:\n";

    private final TaskList taskList;
    private final int taskIndex;
    private final boolean isDone;

    /**
     * Initializes a new MarkTaskCommand with the specified task list, task index, and done status.
     *
     * @param taskList The task list to be modified.
     * @param taskIndex The index of the task to be marked.
     * @param isDone The new done status of the task.
     */
    public MarkTaskCommand(TaskList taskList, int taskIndex, boolean isDone) {
        this.taskList = taskList;
        this.taskIndex = taskIndex;
        this.isDone = isDone;
    }

    @Override
    public CommandResult<Task> execute() {
        if (taskIndex < 0 || taskIndex >= taskList.getTaskCount()) {
            return new CommandResult<>(false, "Task index out of range");
        }

        Task task = taskList.getTask(taskIndex);
        task.setDone(isDone);

        String message = (isDone ? MESSAGE_MARKED : MESSAGE_UNMARKED) + task;
        return new CommandResult<>(true, message);
    }
}
