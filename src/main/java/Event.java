import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected String from;
    protected String to;
    LocalDate fromDateOnly = null;
    LocalDateTime fromDateAndTime = null;

    LocalDate toDateOnly = null;
    LocalDateTime toDateAndTime = null;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        this.taskType = "event";
    }

    public String getDescription() {
        return super.getDescription() + " /from " + from + " /to " + to;
    }

    @Override
    public String toString() {
        String fromDate = null;
        if (!from.contains("T")) {
            fromDateOnly = LocalDate.parse(from);
            fromDate = fromDateOnly.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        } else {
            fromDateAndTime = LocalDateTime.parse(from);
            fromDate = fromDateAndTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma"));
        }

        String toDate = null;
        if (!to.contains("T")) {
            toDateOnly = LocalDate.parse(to);
            toDate = toDateOnly.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        } else {
            toDateAndTime = LocalDateTime.parse(to);
            toDate = toDateAndTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma"));
        }
        return "[E]" + super.toString() + " (from: " + fromDate + " to: " + toDate + ")";
    }
}
