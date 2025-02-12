package oogway.logic.commands;

import oogway.storage.TaskList;
import oogway.tasks.Deadline;
import oogway.tasks.Event;
import oogway.tasks.Task;
import oogway.tasks.ToDo;

/**
 * Adds a task to the task list.
 */
public class AddTaskCommand extends Command {
    private final TaskList taskList;
    private final String taskType;
    private final String taskDescription;
    private final String by;
    private final String from;
    private final String to;

    /**
     * Initializes a new AddTaskCommand with the specified task list, task type, task description, deadline, and event
     * timing.
     * @param taskList The task list to add the task to.
     * @param taskType The type of task to add.
     * @param taskDescription The description of the task.
     * @param by The deadline of the task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public AddTaskCommand(TaskList taskList, String taskType, String taskDescription,
                          String by, String from, String to) {
        this.taskList = taskList;
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.by = by;
        this.from = from;
        this.to = to;
    }

    /**
     * Initializes a new AddTaskCommand for a TODO task with the specified task list, task type, and task description.
     * @param taskList The task list to add the task to.
     * @param taskType The type of task to add.
     * @param taskDescription The description of the task.
     */
    public AddTaskCommand(TaskList taskList, String taskType, String taskDescription) {
        this(taskList, taskType, taskDescription, null, null, null);
    }

    /**
     * Initializes a new AddTaskCommand for a DEADLINE task with the specified task list, task type, task description,
     * and deadline.
     * @param taskList The task list to add the task to.
     * @param taskType The type of task to add.
     * @param taskDescription The description of the task.
     * @param by The deadline of the task.
     */
    public AddTaskCommand(TaskList taskList, String taskType, String taskDescription, String by) {
        this(taskList, taskType, taskDescription, by, null, null);
    }

    /**
     * Initializes a new AddTaskCommand for an EVENT task with the specified task list, task type, task description,
     * start time, and end time.
     * @param taskList The task list to add the task to.
     * @param taskType The type of task to add.
     * @param taskDescription The description of the task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public AddTaskCommand(TaskList taskList, String taskType, String taskDescription, String from, String to) {
        this(taskList, taskType, taskDescription, null, from, to);
    }

    @Override
    public CommandResult<Task>execute() {
        Task task;

        switch (taskType) {
        case "todo" -> task = new ToDo(taskDescription, false);
        case "deadline" -> {
            try {
                task = new Deadline(taskDescription, false, by);
            } catch (Exception e) {
                return new CommandResult<>(false, e.getMessage());
            }
        }
        case "event" -> {
            try {
                task = new Event(taskDescription, false, from, to);
            } catch (Exception e) {
                return new CommandResult<>(false, e.getMessage());
            }
        }
        default -> {
            return new CommandResult<>(false, "Invalid task type!");
        }
        }

        taskList.addTask(task);

        String message = "Alright, I have noted down the task:\n  "
                + task + "\nNow you have " + taskList.getTaskCount() + " tasks in the list.";

        return new CommandResult<>(true, message, task);
    }
}
