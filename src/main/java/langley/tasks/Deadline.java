package langley.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    String byString;
    LocalDate byDate;

    public Deadline(String description, String by) {
        super(description);
        try {
            this.byDate = LocalDate.parse(by);
            this.byString = null;
        } catch (DateTimeParseException e) {
            this.byString = by;
            this.byDate = null;
        }
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String toString() {
        String by = (byDate != null) ? byDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : byString;
        return super.toString() + " (by: " + by + ")";
    }
}
