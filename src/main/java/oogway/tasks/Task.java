package oogway.tasks;

/**
 * Represents an abstract task with a description and a completion status.
 * Subclasses must define the task type.
 */
public abstract class Task {

    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      Indicates whether the task is completed.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the task type identifier.
     * Subclasses must implement this method to provide a specific type.
     *
     * @return A string representing the task type.
     */
    public abstract String getTaskType();

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status icon of the task.
     * "X" indicates that the task is completed, while a space " " indicates that it is not.
     *
     * @return A string representing the task's completion status.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // Mark done task with X
    }

    /**
     * Sets the completion status of the task.
     *
     * @param isDone {@code true} if the task is completed, {@code false} otherwise.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns a string representation of the task formatted for saving to a file.
     * The format follows: {@code taskType | completionStatus | description}.
     *
     * @return A formatted string for saving the task.
     */
    public String toSaveFormat() {
        return String.format("%s | %s | %s", getTaskType(), isDone ? "1" : "0", description);
    }

    /**
     * Returns a string representation of the task, including its type, status, and description.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getTaskType(), getStatusIcon(), description);
    }
}
