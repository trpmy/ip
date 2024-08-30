package langley.parser;

public class Parser {

    public static String[] parseCommand(String userInput) {
        String[] parts = userInput.trim().split(" ", 2);
        if (parts.length == 2) {
            return parts;
        } else {
            return new String[] {parts[0], ""};
        }
    }
}
