package langley;

import langley.storage.Storage;
import langley.tasks.Task;
import langley.tasks.ToDo;
import langley.tasks.Deadline;
import langley.tasks.Event;
import langley.tasklist.TaskList;
import langley.ui.Ui;
import langley.parser.Parser;

/**
 * The main class for the Langley application, handling user input, task management, and file storage.
 */
public class Langley {

    /**
     * Main entry point for the Langley application.
     * Initializes the necessary components and starts the command loop for user interaction.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("Langley.txt");
        TaskList taskList = new TaskList();

        ui.printGreeting();
        storage.loadTasks(taskList.getTasks());

        while (true) {
            String userInput = ui.getUserInput();
            String[] command = Parser.parseCommand(userInput);

            if (command[0].equalsIgnoreCase("bye")) {
                ui.printGoodbye();
                break;
            } else if (command[0].equalsIgnoreCase("list")) {
                taskList.listTasks();
            } else if (command[0].equalsIgnoreCase("mark")) {
                try {
                    int index = Integer.parseInt(command[1]) - 1;
                    taskList.markTask(index);
                    ui.print("I've marked this:");
                    ui.print("  " + taskList.getTask(index));
                    storage.saveTasks(taskList.getTasks());
                } catch (Exception e) {
                    ui.print("Error: Invalid index.");
                }
            } else if (command[0].equalsIgnoreCase("unmark")) {
                try {
                    int index = Integer.parseInt(command[1]) - 1;
                    taskList.unmarkTask(index);
                    ui.print("I've unmarked this:");
                    ui.print("  " + taskList.getTask(index));
                    storage.saveTasks(taskList.getTasks());
                } catch (Exception e) {
                    ui.print("Error: Invalid index.");
                }
            } else if (command[0].equalsIgnoreCase("todo")) {
                String description = command[1].trim();
                if (!description.isEmpty()) {
                    taskList.addTask(new ToDo(description));
                    ui.print("Added a new ToDo: " + description);
                    storage.saveTasks(taskList.getTasks());
                } else {
                    ui.print("Error: ToDo description cannot be empty.");
                }
            } else if (command[0].equalsIgnoreCase("deadline")) {
                String[] parts = command[1].split("/by", 2);
                if (parts.length == 2 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty()) {
                    taskList.addTask(new Deadline(parts[0].trim(), parts[1].trim()));
                    ui.print("Added a new Deadline: " + parts[0].trim() + " (by: " + parts[1].trim() + ")");
                    storage.saveTasks(taskList.getTasks());
                } else {
                    ui.print("Error: Deadline command format is 'deadline x /by time'.");
                }
            } else if (command[0].equalsIgnoreCase("event")) {
                String[] parts = command[1].split("/from|/to", 3);
                if (parts.length == 3 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty() && !parts[2].trim().isEmpty()) {
                    taskList.addTask(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    ui.print("Added a new Event: " + parts[0].trim() + " (from: " + parts[1].trim() + " to: " + parts[2].trim() + ")");
                    storage.saveTasks(taskList.getTasks());
                } else {
                    ui.print("Error: Event command format is 'event x /from time /to time'.");
                }
            } else if (command[0].equalsIgnoreCase("delete")) {
                try {
                    int index = Integer.parseInt(command[1]) - 1;
                    Task task = taskList.getTask(index);
                    taskList.deleteTask(index);
                    ui.print("Deleted: " + task);
                    storage.saveTasks(taskList.getTasks());
                } catch (Exception e) {
                    ui.print("Error: Invalid index.");
                }
            } else {
                ui.print("Unknown command. Available commands: list, mark, unmark, todo, deadline, event, delete, bye.");
            }
        }

        ui.close();
    }
}
