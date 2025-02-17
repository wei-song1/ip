package codsworth.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents task. A <code>Task</code> object corresponds to a task represented by a name
 */
public abstract class Task {
    protected String description;
    protected String taskType;
    protected boolean isDone;

    /**
     * Initalisation method of Task
     *
     * @param description Name of the task.
     */
    public Task(String description) {
        assert !description.isEmpty() : "Description cannot be empty";
        this.description = description;
        this.isDone = false;
        this.taskType = "";
    }

    public void setDoneOrUndone(String type) {
        assert type.equals("mark") | type.equals("unmark") : "Type must be 'mark' or 'unmark'";
        this.isDone = type.equals("mark");
    }

    public String getTaskType() {
        return this.taskType;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Converts date string saved in command into proper date format.
     * Example: "01-01-2001" converts to "01 Jan 2001"
     *
     * @param date Date in either "YYYY-MM-DD" or "YYYY-MM-DDTHH:MM" format.
     * @return Formatted string (Example: "01 Jan 2001" or "01 Jan 2001 03:00PM")
     */
    public String convertDateToPrint(String date) {
        String output;
        if (!date.contains("T")) {
            LocalDate dateOnly = LocalDate.parse(date);
            output = dateOnly.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        } else {
            LocalDateTime dateAndTime = LocalDateTime.parse(date);
            output = dateAndTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma"));
        }
        return output;
    }

    @Override
    public String toString() {
        return isDone
                ? "[X] " + this.description
                : "[ ] " + this.description;
    }
}
