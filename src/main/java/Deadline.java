import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime endDateTime;
    protected DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(("MMM dd yyyy (hh:mm a)"));

    public Deadline(String description, boolean isDone, String dateTimeString) {
        super(description, isDone);

        try {
            endDateTime = LocalDateTime.parse(dateTimeString, inputFormatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date. Follow this format: [yyyy-MM-dd HHmm]");
        }
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String toSaveFormat() {
        return String.format("%s | %s", super.toSaveFormat(), endDateTime.format(inputFormatter));
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), endDateTime.format(outputFormatter));
    }
}