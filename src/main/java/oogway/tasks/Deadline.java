package oogway.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task, which is a task with a specific end date-time.
 * It extends the {@link Task} class and is identified by the task type "D".
 */
public class Deadline extends Task {

    protected LocalDateTime endDateTime;
    protected DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(("MMM dd yyyy (hh:mm a)"));

    /**
     * Constructs a new Deadline task with the given description, completion status,
     * and end date-time.
     *
     * @param description    The description of the deadline.
     * @param isDone         Indicates whether the deadline is completed.
     * @param dateTimeString The end date and time in the format "yyyy-MM-dd HHmm".
     * @throws IllegalArgumentException If the provided date-time format is incorrect.
     */
    public Deadline(String description, boolean isDone, String dateTimeString) {
        super(description, isDone);

        try {
            endDateTime = LocalDateTime.parse(dateTimeString, inputFormatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date!\n"
                    + "Date Format: /by <yyyy-MM-dd HHmm>");
        }
    }

    /**
     * Returns the task type of the deadline.
     * The task type is represented as "D".
     *
     * @return A string representing the task type, which is "D" for deadlines.
     */
    @Override
    public String getTaskType() {
        return "D";
    }

    /**
     * Returns the end date and time of the deadline.
     *
     * @return The end date and time as a {@code LocalDateTime} object.
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public LocalDateTime getComparisonDateTime() {
        return endDateTime;
    }

    /**
     * Returns a string representation of the event formatted for saving to a file.
     * The format follows: {@code taskType | completionStatus | description | endDateTime}.
     *
     * @return A formatted string for saving the event task.
     */
    @Override
    public String toSaveFormat() {
        return String.format("%s | %s", super.toSaveFormat(), endDateTime.format(inputFormatter));
    }

    /**
     * Returns a string representation of the event task, including its type, status,
     * description and end date-time.
     *
             * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), endDateTime.format(outputFormatter));
    }
}
