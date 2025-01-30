package codsworth.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected String by;
    private LocalDate dateOnly = null;
    private LocalDateTime dateAndTime = null;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.taskType = "deadline";
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " /by " + by;
    }

    @Override
    public String toString() {
        String output = null;
        if (!by.contains("T")) {
            dateOnly = LocalDate.parse(by);
            output = dateOnly.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        } else {
            dateAndTime = LocalDateTime.parse(by);
            output = dateAndTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma"));
        }
        return "[D]" + super.toString() + " (by: " + output + ")";
    }
}
