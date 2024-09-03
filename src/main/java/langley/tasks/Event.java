package langley.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start time and end time.
 */
public class Event extends Task {
    LocalDate fromDate;
    String fromString;
    LocalDate toDate;
    String toString;

    public Event(String description, String from, String to) {
        super(description);
        try {
            this.fromDate = LocalDate.parse(from);
            this.fromString = null;
            this.toDate = LocalDate.parse(to);
            this.toString = null;
        } catch (DateTimeParseException e) {
            this.fromDate = null;
            this.fromString = from;
            this.toDate = null;
            this.toString = to;
        }
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        String from = (fromDate != null) ? fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : fromString;
        String to = (toDate != null) ? toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : toString;
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
