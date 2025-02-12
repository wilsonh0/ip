package oogway.tasks;

/**
 * Represents a To-Do task, which is a basic task without a specific deadline or event time.
 * It extends the {@link Task} class and is identified by the task type "T".
 */
public class ToDo extends Task {

    /**
     * Constructs a new To-Do task with the specified description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      Indicates whether the task has been completed.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the task type of the To-Do task.
     * The task type is represented as "T".
     *
     * @return A string representing the task type, which is "T" for To-Do tasks.
     */
    @Override
    public String getTaskType() {
        return "T";
    }
}
