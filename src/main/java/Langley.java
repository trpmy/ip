import java.util.ArrayList;
import java.util.Scanner;

public class Langley {
    public static void main(String[] args) {
        String greeting = "Hello, this is Langley.";
        String goodbye = "We will meet again.";
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> messages = new ArrayList<>();

        System.out.println(greeting);

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(goodbye);
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                System.out.println("Messages:");
                for (String message : messages) {
                    System.out.println(message);
                }
            } else {
                messages.add(userInput);
                System.out.println("Langley: " + userInput);
            }
        }

        scanner.close();
    }
}
