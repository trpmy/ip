package langley.tasklist;

import langley.tasks.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task Task to be added to the list.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list by its index.
     *
     * @param index Index of the task to be deleted.
     */
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as completed.
     *
     * @param index Index of the task to be marked.
     */
    public void markTask(int index) {
        tasks.get(index).mark();
    }

    /**
     * Unmarks the task at the specified index, marking it as incomplete.
     *
     * @param index Index of the task to be unmarked.
     */
    public void unmarkTask(int index) {
        tasks.get(index).unmark();
    }

    /**
     * Lists all tasks currently in the task list, printing them with their index and status.
     */
    public void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
    public void findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.size() > 0) {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }
        } else {
            System.out.println("No matching tasks found.");
        }
    }
}
