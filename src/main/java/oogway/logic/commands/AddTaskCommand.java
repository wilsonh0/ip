package oogway.logic.commands;

import oogway.storage.TaskList;
import oogway.tasks.Deadline;
import oogway.tasks.Event;
import oogway.tasks.Task;
import oogway.tasks.TaskType;
import oogway.tasks.ToDo;

/**
 * Adds a task to the task list.
 */
public class AddTaskCommand extends Command {
    private final TaskList taskList;
    private final TaskType taskType;
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
    public AddTaskCommand(TaskList taskList, TaskType taskType, String taskDescription,
                          String by, String from, String to) {
        this.taskList = taskList;
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.by = by;
        this.from = from;
        this.to = to;
    }

    /**
     * Initializes a new AddTaskCommand for a TODO task with the specified task list and task description.
     * @param taskList The task list to add the task to.
     * @param taskDescription The description of the task.
     */
    public static AddTaskCommand createTodo(TaskList taskList, String taskDescription) {
        return new AddTaskCommand(taskList, TaskType.TODO, taskDescription, null, null, null);
    }

    /**
     * Initializes a new AddTaskCommand for a DEADLINE task with the specified task list, task description,
     * and deadline.
     * @param taskList The task list to add the task to.
     * @param taskDescription The description of the task.
     * @param by The deadline of the task.
     */
    public static AddTaskCommand createDeadline(TaskList taskList, String taskDescription, String by) {
        return new AddTaskCommand(taskList, TaskType.DEADLINE, taskDescription, by, null, null);
    }

    /**
     * Initializes a new AddTaskCommand for an EVENT task with the specified task list, task description,
     * and event timing.
     * @param taskList The task list to add the task to.
     * @param taskDescription The description of the task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public static AddTaskCommand createEvent(TaskList taskList, String taskDescription,
                                             String from, String to) {
        return new AddTaskCommand(taskList, TaskType.EVENT, taskDescription, null, from, to);
    }

    @Override
    public CommandResult<Task>execute() {
        Task task;

        try {
            task = switch (taskType) {
            case TODO -> new ToDo(taskDescription, false);
            case DEADLINE -> new Deadline(taskDescription, false, by);
            case EVENT -> new Event(taskDescription, false, from, to);
            };
        } catch (Exception e) {
            return new CommandResult<>(false, e.getMessage());
        }

        taskList.addTask(task);

        String message = "Alright, I have noted down the task:\n  "
                + task + "\n\nNow you have " + taskList.getTaskCount() + " tasks in the list.";

        return new CommandResult<>(true, message, task);
    }
}
