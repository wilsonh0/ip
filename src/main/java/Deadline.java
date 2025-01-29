public class Deadline extends Task {

    protected String endDateTime;

    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.endDateTime = by;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    public  String getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String toSaveFormat() {
        return String.format("%s | %s", super.toSaveFormat(), endDateTime);
    }

    @Override
    public String toString() {
        return String.format("%s (by: %s)", super.toString(), endDateTime);
    }
}