package codsworth.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the event task. A <code>Task</code> object corresponds to a task
 * represented by a name, start date and end date
 * e.g., <code>event [task name] /from DD-MM-YYYY /to /from DD-MM-YYYY</code>
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Initalisation method of Event
     *
     * @param description Name of the task.
     * @param from Start date of the task.
     * @param to End date of the task.
     */
    public Event(String description, String from, String to) {
        super(description);
        assert from != null : "Date must not be null";
        assert to != null : "Date must not be null";
        this.from = from;
        this.to = to;
        this.taskType = "event";
    }

    /**
     * Returns event in the same way that the user would have inputted
     *
     * @return Event task in the same way as the user would have inputted
     */
    public String getDescription() {
        return super.getDescription() + " /from " + from + " /to " + to;
    }

    /**
     * Returns the event in the formatted way to show in lists and prints
     *
     * @return Event task formatted for lists and prints
     */
    @Override
    public String toString() {
        String fromDate = convertDateToPrint(from);
        String toDate = convertDateToPrint(to);
        return "[E]" + super.toString() + " (from: " + fromDate + " to: " + toDate + ")";
    }
}
