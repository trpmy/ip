package langley.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TaskTest {

    @Test
    public void testFromStringToDo() {
        // Create a ToDo task
        Task task1 = new ToDo("Sample ToDo 1");

        // Convert task1 to a string and then back to a task using fromString
        Task parsedTask = Task.fromString(task1.toString());

        // Validate the toString() of both tasks are equal
        assert parsedTask != null;
        assertEquals(task1.toString(), parsedTask.toString());
    }

    @Test
    public void testFromStringDeadline() {
        // Create a Deadline task
        Task task1 = new Deadline("Sample Deadline 1", "2024-08-30");

        // Convert task1 to a string and then back to a task using fromString
        Task parsedTask = Task.fromString(task1.toString());

        // Validate the toString() of both tasks are equal
        assert parsedTask != null;
        assertEquals(task1.toString(), parsedTask.toString());
    }

    @Test
    public void testFromStringEvent() {
        // Create an Event task
        Task task1 = new Event("Sample Event 1", "2024-08-30", "2024-09-05");

        // Convert task1 to a string and then back to a task using fromString
        Task parsedTask = Task.fromString(task1.toString());

        // Validate the toString() of both tasks are equal
        assert parsedTask != null;
        assertEquals(task1.toString(), parsedTask.toString());
    }
}
