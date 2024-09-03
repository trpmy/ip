package langley.tasks;

/**
 * Represents a ToDo task with only a description.
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String getType() {
        return "T";
    }
}
