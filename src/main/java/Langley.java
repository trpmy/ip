import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Langley {

    // Task Classes
    static abstract class Task {
        String description;
        boolean isMarked;

        Task(String description) {
            this.description = description;
            this.isMarked = false;
        }

        void mark() {
            this.isMarked = true;
        }

        void unmark() {
            this.isMarked = false;
        }

        abstract String getType();

        @Override
        public String toString() {
            return "[" + getType() + "][" + (isMarked ? "X" : " ") + "] " + description;
        }

        static Task fromString(String line) {
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

    static class ToDo extends Task {
        ToDo(String description) {
            super(description);
        }

        @Override
        String getType() {
            return "T";
        }
    }

    static class Deadline extends Task {
        String byString;
        LocalDate byDate;

        Deadline(String description, String by) {
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
        String getType() {
            return "D";
        }

        @Override
        public String toString() {
            String by = (byDate != null) ? byDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : byString;
            return super.toString() + " (by: " + by + ")";
        }
    }

    static class Event extends Task {
        String fromString;
        String toString;
        LocalDate fromDate;
        LocalDate toDate;

        Event(String description, String from, String to) {
            super(description);
            try {
                this.fromDate = LocalDate.parse(from);
                this.fromString = null;
            } catch (DateTimeParseException e) {
                this.fromString = from;
                this.fromDate = null;
            }

            try {
                this.toDate = LocalDate.parse(to);
                this.toString = null;
            } catch (DateTimeParseException e) {
                this.toString = to;
                this.toDate = null;
            }
        }

        @Override
        String getType() {
            return "E";
        }

        @Override
        public String toString() {
            String from = (fromDate != null) ? fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : fromString;
            String to = (toDate != null) ? toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : toString;
            return super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }

    // Storage Class
    static class Storage {
        private String filePath;

        Storage(String filePath) {
            this.filePath = filePath;
        }

        void saveTasks(ArrayList<Task> tasks) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (Task task : tasks) {
                    writer.write(task.toString());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error: Unable to save tasks to file.");
            }
        }

        void loadTasks(ArrayList<Task> tasks) {
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

    // TaskList Class
    static class TaskList {
        private ArrayList<Task> tasks;

        TaskList() {
            this.tasks = new ArrayList<>();
        }

        void addTask(Task task) {
            tasks.add(task);
        }

        void deleteTask(int index) {
            tasks.remove(index);
        }

        Task getTask(int index) {
            return tasks.get(index);
        }

        int size() {
            return tasks.size();
        }

        ArrayList<Task> getTasks() {
            return tasks;
        }

        void markTask(int index) {
            tasks.get(index).mark();
        }

        void unmarkTask(int index) {
            tasks.get(index).unmark();
        }

        void listTasks() {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    // Ui Class
    static class Ui {
        private Scanner scanner;

        Ui() {
            this.scanner = new Scanner(System.in);
        }

        void printGreeting() {
            System.out.println("Hello, this is Langley.");
        }

        void printGoodbye() {
            System.out.println("We will meet again.");
        }

        String getUserInput() {
            System.out.print("You: ");
            return scanner.nextLine();
        }

        void print(String message) {
            System.out.println(message);
        }

        void close() {
            scanner.close();
        }
    }

    // Parser Class
    static class Parser {
        static String[] parseCommand(String userInput) {
            return userInput.trim().split(" ", 2);
        }
    }

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
                ui.print("Error: Unknown command. Available commands: list, mark, unmark, todo, deadline, event, delete, bye");
            }
        }

        ui.close();
    }
}
