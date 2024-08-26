import java.util.Scanner;

public class Langley {
    public static void main(String[] args) {
        String greeting = "Hello, this is Langley.";
        String goodbye = "We will meet again.";
        Scanner scanner = new Scanner(System.in);

        System.out.println(greeting);

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(goodbye);
                break;
            } else {
                System.out.println("Langley: " + userInput);
            }
        }

        scanner.close();
    }
}
