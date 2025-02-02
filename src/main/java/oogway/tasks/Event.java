package oogway.tasks;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task, which is a task with a defined start and end date-time.
 * It extends the {@link Task} class and is identified by the task type "E".
 */
public class Event extends Task {

    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;

    protected DateTimeFormatter startDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected DateTimeFormatter endDateFormatter = DateTimeFormatter.ofPattern("HHmm");

    protected DateTimeFormatter outputStartDateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy (hh:mm a");
    protected DateTimeFormatter outputEndDateFormatter = DateTimeFormatter.ofPattern("hh:mm a)");

    /**
     * Constructs a new event task with the given description, completion status,
     * start date-time, and end time.
     *
     * @param description        The description of the event.
     * @param isDone             Indicates whether the event is completed.
     * @param startDateTimeString The start date and time in the format "yyyy-MM-dd HHmm".
     * @param endTimeString      The end time in the format "HHmm".
     * @throws IllegalArgumentException If the provided date-time format is incorrect.
     */
    public Event(String description, boolean isDone, String startDateTimeString, String endTimeString) {
        super(description, isDone);

        try {
            this.startDateTime = LocalDateTime.parse(startDateTimeString, startDateFormatter);

            LocalTime endTime = LocalTime.parse(endTimeString, endDateFormatter);
            this.endDateTime = startDateTime.toLocalDate().atTime(endTime);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date. Follow this format: [yyyy-MM-dd HHmm]");
        }
    }

    /**
     * Returns the task type of the event.
     * The task type is represented as "E".
     *
     * @return A string representing the task type, which is "E" for events.
     */
    @Override
    public String getTaskType() {
        return "E";
    }

    /**
     * Returns the start date and time of the event.
     *
     * @return The start date and time as a {@code LocalDateTime} object.
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Returns the end date and time of the event.
     *
     * @return The end date and time as a {@code LocalDateTime} object.
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns a string representation of the event formatted for saving to a file.
     * The format follows: {@code taskType | completionStatus | description | startDateTime | endTime}.
     *
     * @return A formatted string for saving the event task.
     */
    @Override
    public String toSaveFormat() {
        return String.format("%s | %s | %s", super.toSaveFormat(), startDateTime.format(startDateFormatter),
                        endDateTime.format(endDateFormatter));
    }

    /**
     * Returns a string representation of the event task, including its type, status,
     * description, start date-time, and end time.
     *
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        return String.format("%s (at: %s to %s)", super.toString(), startDateTime.format(outputStartDateFormatter),
                endDateTime.format(outputEndDateFormatter));
    }
}