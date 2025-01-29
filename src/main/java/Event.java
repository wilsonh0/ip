public class Event extends Task {

    protected String startDateTime;
    protected String endDateTime;

    public Event(String description, boolean isDone, String startDateTime, String endDateTime) {
        super(description, isDone);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String toSaveFormat() {
        return String.format("%s | %s | %s", super.toSaveFormat(), startDateTime, endDateTime);
    }

    @Override
    public String toString() {
        return String.format("%s (at: %s to %s)", super.toString(), startDateTime, endDateTime);
    }
}