import java.util.ArrayList;
import java.util.Scanner;

public class Langley {
    static class Message {
        String text;
        boolean isMarked;
        Message(String text) {
            this.text = text;
            this.isMarked = false;
        }
        void mark() {
            this.isMarked = true;
        }
        void unmark() {
            this.isMarked = false;
        }
        @Override
        public String toString() {
            return "[" + (isMarked ? "X" : " ") + "] " + text;
        }
    }

    public static void main(String[] args) {
        String greeting = "Hello, this is Langley.";
        String goodbye = "We will meet again.";
        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> messages = new ArrayList<>();

        System.out.println(greeting);

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(goodbye);
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                for (int i = 0; i < messages.size(); i++) {
                    System.out.println((i + 1) + ". " + messages.get(i));
                }
            } else if (userInput.toLowerCase().startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(5)) - 1;
                    if (index >= 0 && index < messages.size()) {
                        messages.get(index).mark();
                        System.out.println("I've marked this:");
                        System.out.println("  " + messages.get(index));
                    } else {
                        System.out.println("You don't have that many messages.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("That's not even a number.");
                }
            } else if (userInput.toLowerCase().startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(userInput.substring(7)) - 1;
                    if (index >= 0 && index < messages.size()) {
                        messages.get(index).unmark();
                        System.out.println("I've unmarked this:");
                        System.out.println("  " + messages.get(index));
                    } else {
                        System.out.println("Invalid message number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid message number.");
                }
            } else {
                messages.add(new Message(userInput));
                System.out.println("Langley: " + userInput);
            }
        }

        scanner.close();
    }
}
