package codsworth.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the deadline task. A <code>Task</code> object corresponds to a task represented by a name and a by date
 * e.g., <code>deadline [task name] /by DD-MM-YYYY</code>
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Initalisation method of Deadline
     *
     * @param description Name of the task.
     * @param by Deadline date of the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.taskType = "deadline";
    }

    /**
     * Returns deadline in the same way that the user would have inputted
     *
     * @return Deadline task in the same way as the user would have inputted
     */
    @Override
    public String getDescription() {
        return super.getDescription() + " /by " + by;
    }

    /**
     * Returns the deadline in the formatted way to show in lists and prints
     *
     * @return Deadline task formatted for lists and prints
     */
    @Override
    public String toString() {
        String output;
        if (!by.contains("T")) {
            LocalDate dateOnly = LocalDate.parse(by);
            output = dateOnly.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        } else {
            LocalDateTime dateAndTime = LocalDateTime.parse(by);
            output = dateAndTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma"));
        }
        return "[D]" + super.toString() + " (by: " + output + ")";
    }
}
