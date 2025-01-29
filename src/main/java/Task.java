public abstract class Task {

    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public abstract String getTaskType();

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String toSaveFormat() {
        return String.format("%s | %s | %s", getTaskType(), isDone ? "1" : "0", description);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getTaskType(), getStatusIcon(), description);
    }
}