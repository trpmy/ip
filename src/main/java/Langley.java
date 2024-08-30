import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Langley {
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

    public static void main(String[] args) {
        String greeting = "Hello, this is Langley.";
        String goodbye = "We will meet again.";
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        loadTasks(tasks);

        System.out.println(greeting);

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(goodbye);
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else if (userInput.toLowerCase().startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(5)) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).mark();
                        System.out.println("I've marked this:");
                        System.out.println("  " + tasks.get(index));
                        saveTasks(tasks);
                    } else {
                        System.out.println("Error: Index non-existent. Enter an in-range index.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid index. Enter a valid index.");
                }
            } else if (userInput.toLowerCase().startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).unmark();
                        System.out.println("I've unmarked this:");
                        System.out.println("  " + tasks.get(index));
                        saveTasks(tasks);
                    } else {
                        System.out.println("Error: Index non-existent. Enter an in-range index.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid index. Enter a valid index.");
                }
            } else if (userInput.toLowerCase().startsWith("todo ")) {
                String description = userInput.substring(5).trim();
                if (!description.isEmpty()) {
                    tasks.add(new ToDo(description));
                    System.out.println("Added a new ToDo: " + description);
                    saveTasks(tasks);
                } else {
                    System.out.println("Error: ToDo description cannot be empty.");
                }
            } else if (userInput.toLowerCase().startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split("/by", 2);
                if (parts.length == 2 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty()) {
                    tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
                    System.out.println("Added a new Deadline: " + parts[0].trim() + " (by: " + parts[1].trim() + ")");
                    saveTasks(tasks);
                } else {
                    System.out.println("Error: Deadline command format is 'deadline x /by time'.");
                }
            } else if (userInput.toLowerCase().startsWith("event ")) {
                String[] parts = userInput.substring(6).split("/from|/to");
                if (parts.length == 3 && !parts[0].trim().isEmpty() && !parts[1].trim().isEmpty() && !parts[2].trim().isEmpty()) {
                    tasks.add(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    System.out.println("Added a new Event: " + parts[0].trim() + " (from: " + parts[1].trim() + " to: " + parts[2].trim() + ")");
                    saveTasks(tasks);
                } else {
                    System.out.println("Error: Event command format is 'event x /from time1 /to time2'.");
                }
            } else if (userInput.toLowerCase().startsWith("delete ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removedTask = tasks.remove(index);
                        System.out.println("I've deleted this task:");
                        System.out.println("  " + removedTask);
                        saveTasks(tasks);
                    } else {
                        System.out.println("Error: Index non-existent. Enter an in-range index.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid index. Enter a valid index.");
                }
            } else {
                System.out.println("Error: Invalid command. Use 'todo', 'deadline', or 'event'. To exit, use 'bye'.");
            }
        }

        scanner.close();
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Langley.txt"))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to save tasks to file.");
        }
    }

    private static void loadTasks(ArrayList<Task> tasks) {
        File file = new File("Langley.txt");
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
