package langley.ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Prints a greeting message to welcome the user.
     */
    public void printGreeting() {
        System.out.println("Hello, this is Langley.");
    }

    /**
     * Retrieves user input from the console.
     *
     * @return The user's input as a string.
     */
    public String getUserInput() {
        System.out.print("You: ");
        return scanner.nextLine();
    }

    /**
     * Prints a goodbye message to the user before exiting.
     */
    public void printGoodbye() {
        System.out.println("We will meet again.");
    }

    /**
     * Prints a given message to the console.
     *
     * @param message Message to be printed.
     */
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Closes the Scanner used for reading input from the console.
     */
    public void close() {
        scanner.close();
    }
}
