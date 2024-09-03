package langley.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import langley.tasks.Task;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given list of tasks to the file specified by the file path.
     *
     * @param tasks List of tasks to save.
     * @throws IOException If an I/O error occurs during file writing.
     */

    public void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to save tasks to file.");
        }
    }

    /**
     * Loads tasks from the file into the provided task list.
     * If the file does not exist, no tasks are loaded.
     *
     * @param tasks List to populate with tasks from the file.
     * @throws IOException If an I/O error occurs during file reading.
     */

    public void loadTasks(ArrayList<Task> tasks) {
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromString(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error: Unable to load tasks from file.");
            }
        }
    }
}
