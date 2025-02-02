package oogway.commands;

import oogway.storage.TaskList;
import oogway.tasks.Deadline;
import oogway.tasks.Event;
import oogway.tasks.Task;
import oogway.tasks.ToDo;

public class AddTaskCommand extends Command<Task> {
    private final TaskList taskList;
    private final String taskType;
    private final String taskDescription;
    private final String by;
    private final String from;
    private final String to;

    // For Event
    public AddTaskCommand(TaskList taskList, String taskType, String taskDescription,
                          String by, String from, String to) {
        this.taskList = taskList;
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.by = by;
        this.from = from;
        this.to = to;
    }

    // To-do
    public AddTaskCommand(TaskList taskList, String taskType, String taskDescription) {
        this(taskList, taskType, taskDescription, null, null, null);
    }

    // Deadline
    public AddTaskCommand(TaskList taskList, String taskType, String taskDescription, String by) {
        this(taskList, taskType, taskDescription, by, null, null);
    }

    // Event
    public AddTaskCommand(TaskList taskList, String taskType, String taskDescription, String from, String to) {
        this(taskList, taskType, taskDescription, null, from, to);
    }

    // Event

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
