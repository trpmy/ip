package langley;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Implements a GUI for Langley
 */
public class LangleyGuiApp extends Application {
    private VBox dialogContainer;
    private TextField userInput;

    // Avatar images
    private Image userAvatar;
    private Image chatbotAvatar;

    public static void main(String[] args) {
        launch(args); // Starts the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Load avatar images
        userAvatar = new Image(this.getClass().getResourceAsStream("/langley/user.png"));
        chatbotAvatar = new Image(this.getClass().getResourceAsStream("/langley/langley.jpg"));

        // Setting up the window (Stage) and layout
        primaryStage.setTitle("Langley");

        // VBox to hold the conversation
        dialogContainer = new VBox();
        dialogContainer.setSpacing(10);
        dialogContainer.setPadding(new Insets(10, 20, 10, 20));

        // ScrollPane to make the conversation scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(500);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: #FFFFFF; -fx-background-color: transparent;");

        // TextField to capture user input
        userInput = new TextField();
        userInput.setPromptText("Enter your message here...");
        userInput.setPrefHeight(40);
        userInput.setStyle("-fx-font-size: 14px;");

        userInput.setOnAction(event -> handleUserInput());

        // Send button
        Button sendButton = new Button("Send");
        sendButton.setPrefHeight(40);
        sendButton.setDefaultButton(true);
        sendButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        sendButton.setOnAction(event -> handleUserInput());

        // HBox to arrange the TextField and Button in one line
        HBox inputContainer = new HBox(10, userInput, sendButton);
        inputContainer.setPadding(new Insets(10));
        inputContainer.setStyle("-fx-background-color: #eeeeee;");

        // Ensure that the TextField grows to fill space
        HBox.setHgrow(userInput, Priority.ALWAYS);

        // Root layout
        VBox root = new VBox(10, scrollPane, inputContainer);
        root.setPrefSize(400, 600);
        root.setStyle("-fx-background-color: #f0f0f0;");

        Scene scene = new Scene(root);
        scene.getStylesheets().add("langley/ChatbotStyle.css"); // Adding external CSS for styling
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Handle user input and chatbot responses
    private void handleUserInput() {
        String input = userInput.getText();
        if (!input.isEmpty()) {
            // Display user input
            displayUserMessage(input);

            // Process input via Langley chatbot and display response
            String response = getResponse(input);
            displayChatbotMessage(response);

            // Clear input field after submission
            userInput.clear();

        }
    }
    // Method to display user's message with their avatar
    private void displayUserMessage(String message) {
        Label userText = new Label(message);
        userText.getStyleClass().add("user-label");

        // Enable text wrapping and set minimum height
        userText.setWrapText(true);
        userText.setMinHeight(Region.USE_PREF_SIZE); // Or Region.USE_COMPUTED_SIZE
        //userText.setMinHeight(-Infinity); // This ensures no minimum height constraint

        ImageView userAvatarView = new ImageView(userAvatar);
        userAvatarView.setFitWidth(50);
        userAvatarView.setFitHeight(50);

        HBox userBox = new HBox(10, userText, userAvatarView);
        userBox.setAlignment(Pos.TOP_RIGHT);
        dialogContainer.getChildren().add(userBox);
    }

    // Method to display chatbot's message with its avatar
    private void displayChatbotMessage(String message) {
        Label chatbotText = new Label(message);
        chatbotText.getStyleClass().add("chatbot-label");

        // Enable text wrapping and set minimum height
        chatbotText.setWrapText(true);
        chatbotText.setMinHeight(Region.USE_PREF_SIZE); // Or Region.USE_COMPUTED_SIZE
        //chatbotText.setMinHeight(-Infinity); // This ensures no minimum height constraint

        ImageView chatbotAvatarView = new ImageView(chatbotAvatar);
        chatbotAvatarView.setFitWidth(50);
        chatbotAvatarView.setFitHeight(50);

        HBox chatbotBox = new HBox(10, chatbotAvatarView, chatbotText);
        chatbotBox.setAlignment(Pos.TOP_LEFT);
        dialogContainer.getChildren().add(chatbotBox);
    }

    // Get response from LangleyApp's logic (stubbed for now)
    private String getResponse(String input) {
        return Langley.handleInput(input);
    }
}
