package langley.parser;

public class Parser {

    /**
     * Parses the user's input into a command and its arguments.
     * The input is split into two parts: the command and the rest.
     *
     * @param userInput The input entered by the user.
     * @return A string array containing the command and its arguments.
     */
    public static String[] parseCommand(String userInput) {
        String[] parts = userInput.trim().split(" ", 2);
        if (parts.length == 2) {
            return parts;
        } else {
            return new String[] {parts[0], ""};
        }
    }
}
