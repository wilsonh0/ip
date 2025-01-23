public class Event extends Task {

    protected String startDateTime;
    protected String endDateTime;

    public Event(String description, String startDateTime, String endDateTime) {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (From: " + startDateTime
                + " to: " + endDateTime + ")";
    }
}