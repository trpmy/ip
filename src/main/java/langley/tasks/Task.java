package langley.tasks;

/**
 * Abstract class representing a generic task with a description and completion status.
 */
public abstract class Task {
    String description;
    public boolean isMarked;

    public Task(String description) {
        this.description = description;
        this.isMarked = false;
    }

    /**
     * Marks the task.
     */
    public void mark() {
        this.isMarked = true;
    }

    /**
     * Unmarks the task.
     */
    public void unmark() {
        this.isMarked = false;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + getType() + "][" + (isMarked ? "X" : " ") + "] " + description;
    }

    /**
     * Converts a string representation of a task back into a Task object.
     * Parses the string for type, status, and task details.
     *
     * @param line The string representing a task.
     * @return The corresponding Task object or null if parsing fails.
     */
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
