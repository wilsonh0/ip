package oogway.tasks;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Represents a comparator for comparing tasks by date.
 */
public class TaskDateTimeComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        LocalDateTime date1 = task1.getComparisonDateTime();
        LocalDateTime date2 = task2.getComparisonDateTime();

        if (date1 == null && date2 == null) {
            return 0;
        } else if (date1 == null) {
            return 1;
        } else if (date2 == null) {
            return -1;
        } else {
            return date1.compareTo(date2);
        }
    }
}
