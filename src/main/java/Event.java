import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;

    protected DateTimeFormatter startDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected DateTimeFormatter endDateFormatter = DateTimeFormatter.ofPattern("HHmm");

    protected DateTimeFormatter outputStartDateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy (hh:mm a");
    protected DateTimeFormatter outputEndDateFormatter = DateTimeFormatter.ofPattern("hh:mm a)");

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

    @Override
    public String getTaskType() {
        return "E";
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String toSaveFormat() {
        return String.format("%s | %s | %s", super.toSaveFormat(), startDateTime.format(startDateFormatter),
                        endDateTime.format(endDateFormatter));
    }

    @Override
    public String toString() {
        return String.format("%s (at: %s to %s)", super.toString(), startDateTime.format(outputStartDateFormatter),
                endDateTime.format(outputEndDateFormatter));
    }
}