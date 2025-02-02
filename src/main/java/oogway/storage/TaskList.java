package oogway.storage;

import oogway.tasks.Task;

import java.util.ArrayList;

/**
 * Represents a list of tasks, providing methods to add, retrieve, and remove tasks.
 */
public class TaskList {

    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a new task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves a task from the task list by its index.
     *
     * @param index The index of the task to retrieve (zero-based).
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns a task from the task list by its index.
     *
     * @param index The index of the task to remove (zero-based).
     * @return The removed task.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The total number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return {@code true} if the list contains no tasks, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns a copy of the task list.
     * This prevents external modifications to the internal task list.
     *
     * @return A new {@code ArrayList} containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}