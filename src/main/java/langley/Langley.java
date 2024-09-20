package langley;

import javafx.application.Platform;
import langley.storage.Storage;
import langley.tasks.Task;
import langley.tasks.ToDo;
import langley.tasks.Deadline;
import langley.tasks.Event;
import langley.tasklist.TaskList;
import langley.parser.Parser;

/**
 * The main class for the Langley application, handling user input, task management, and file storage.
 */
public class Langley {

    private static Storage storage = new Storage("Langley.txt");
    private static TaskList taskList = new TaskList();
    // Static initializer block to load tasks from storage
    static {
        storage.loadTasks(taskList.getTasks());
    }

    /**
     * Main entry point for the Langley application.
     * Initializes the necessary components and starts the command loop for user interaction.
     *
     * @param userInput Command-line arguments (not used).
     */
    public static String handleInput(String userInput) {
            String[] command = Parser.parseCommand(userInput);
            StringBuilder response = new StringBuilder();

            if (command[0].equalsIgnoreCase("bye")) {
                response.append("We will meet again.");
                Platform.exit();
            } else if (command[0].equalsIgnoreCase("list")) {
                response.append(taskList.listTasks());
            } else if (command[0].equalsIgnoreCase("mark")) {
                try {
                    int index = Integer.parseInt(command[1]) - 1;
                    taskList.markTask(index);
                    response.append("I've marked this:");
                    response.append("  " + taskList.getTask(index));
                    storage.saveTasks(taskList.getTasks());
                } catch (Exception e) {
                    response.append("Error: Invalid index.");
                }
            } else if (command[0].equalsIgnoreCase("unmark")) {
                try {
                    int index = Integer.parseInt(command[1]) - 1;
                    taskList.unmarkTask(index);
                    response.append("I've unmarked this:");
                    response.append("  " + taskList.getTask(index));
                    storage.saveTasks(taskList.getTasks());
                } catch (Exception e) {
                    response.append("Error: Invalid index.");
                }
            } else if (command[0].equalsIgnoreCase("todo")) {
                String description = command[1].trim();
                if (!description.isEmpty()) {
                    taskList.addTask(new ToDo(description));
                    response.append("Added a new ToDo: " + description);
                    storage.saveTasks(taskList.getTasks());
                } else {
                    response.append("Error: ToDo description cannot be empty.");
                }
            } else if (command[0].equalsIgnoreCase("deadline")) {
                String[] parts = command[1].split("/by", 2);
                if (parts.length == 2 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty()) {
                    taskList.addTask(new Deadline(parts[0].trim(), parts[1].trim()));
                    response.append("Added a new Deadline: " + parts[0].trim() + " (by: " + parts[1].trim() + ")");
                    storage.saveTasks(taskList.getTasks());
                } else {
                    response.append("Error: Deadline command format is 'deadline x /by time'.");
                }
            } else if (command[0].equalsIgnoreCase("event")) {
                String[] parts = command[1].split("/from|/to", 3);
                if (parts.length == 3 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty() && !parts[2].trim().isEmpty()) {
                    taskList.addTask(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    response.append("Added a new Event: " + parts[0].trim() + " (from: " + parts[1].trim() + " to: " + parts[2].trim() + ")");
                    storage.saveTasks(taskList.getTasks());
                } else {
                    response.append("Error: Event command format is 'event x /from time /to time'.");
                }
            } else if (command[0].equalsIgnoreCase("delete")) {
                try {
                    int index = Integer.parseInt(command[1]) - 1;
                    Task task = taskList.getTask(index);
                    taskList.deleteTask(index);
                    response.append("Deleted: " + task);
                    storage.saveTasks(taskList.getTasks());
                } catch (Exception e) {
                    response.append("Error: Invalid index.");
                }
            } else if (command[0].equalsIgnoreCase("find")) {
                String keyword = command[1].trim();
                if (!keyword.isEmpty()) {
                    response.append(taskList.findTasks(keyword));
                } else {
                    response.append("Error: Please provide a keyword to search for.");
                }
            } else {
                response.append("Unknown command. Available commands: list, mark, unmark, todo, deadline, event, delete, find, bye.");
            }
        return response.toString();
        }


    }
