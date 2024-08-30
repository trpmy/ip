package langley.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Task {
    String description;
    boolean isMarked;

    public Task(String description) {
        this.description = description;
        this.isMarked = false;
    }

    public void mark() {
        this.isMarked = true;
    }

    public void unmark() {
        this.isMarked = false;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + getType() + "][" + (isMarked ? "X" : " ") + "] " + description;
    }

    public static Task fromString(String line) {
        String[] parts = line.split("]", 3);
        String type = parts[0].substring(1);
        boolean isMarked = parts[1].charAt(1) == 'X';
        String description = parts[2].trim();

        Task task = null;

        switch (type) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                String[] deadlineParts = description.split("\\(by: ");
                String deadlineDesc = deadlineParts[0].trim();
                String by = deadlineParts[1].replace(")", "").trim();
                task = new Deadline(deadlineDesc, by);
                break;
            case "E":
                String[] eventParts = description.split("\\(from: | to: ");
                String eventDesc = eventParts[0].trim();
                String from = eventParts[1].trim();
                String to = eventParts[2].replace(")", "").trim();
                task = new Event(eventDesc, from, to);
                break;
            default:
                return null;
        }

        if (task != null && isMarked) {
            task.mark(); // Mark the task as completed
        }

        return task;
    }
}
