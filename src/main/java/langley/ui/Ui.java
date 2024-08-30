package langley.ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner = new Scanner(System.in);

    public void printGreeting() {
        System.out.println("Hello, this is Langley.");
    }

    public String getUserInput() {
        System.out.print("You: ");
        return scanner.nextLine();
    }

    public void printGoodbye() {
        System.out.println("We will meet again.");
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}
