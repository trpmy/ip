package langley.tasklist;

import langley.tasks.Deadline;
import langley.tasks.Task;

import langley.tasks.ToDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class TaskListTest {

    private TaskList taskList = new TaskList();

    @Test
    public void testAddTask() {
        // Arrange
        Task task1 = new ToDo("Sample ToDo");

        // Act
        taskList.addTask(task1);

        // Assert
        assertEquals(1, taskList.getTasks().size());
        assertEquals(task1, taskList.getTask(0));
    }

    @Test
    public void testDeleteTask() {
        // Arrange
        Task task1 = new ToDo("Sample ToDo");
        Task task2 = new Deadline("Sample Deadline", "2024-08-30");
        taskList.addTask(task1);
        taskList.addTask(task2);

        // Act
        taskList.deleteTask(0); // Deleting the first task

        // Assert
        assertEquals(1, taskList.getTasks().size());
        assertEquals(task2, taskList.getTask(0));
    }

    @Test
    public void testMarkTask() {
        // Arrange
        Task task1 = new ToDo("Sample ToDo");
        taskList.addTask(task1);

        // Act
        taskList.markTask(0); // Mark the first task as complete

        // Assert
        assertTrue(taskList.getTask(0).getIsMarked());
    }

    @Test
    public void testUnmarkTask() {
        // Arrange
        Task task1 = new ToDo("Sample ToDo");
        task1.mark(); // Initially mark the task as completed
        taskList.addTask(task1);

        // Act
        taskList.unmarkTask(0); // Unmark the first task

        // Assert
        assertFalse(taskList.getTask(0).getIsMarked());
    }

    @Test
    public void testListTasks() {
        // Arrange
        Task task1 = new ToDo("Sample ToDo");
        Task task2 = new Deadline("Sample Deadline", "2024-08-30");
        taskList.addTask(task1);
        taskList.addTask(task2);

        // Act & Assert
        // We can't directly assert system output, so we will check task order and content
        assertEquals(task1, taskList.getTask(0));
        assertEquals(task2, taskList.getTask(1));
    }
}
