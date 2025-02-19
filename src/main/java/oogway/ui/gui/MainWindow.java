package oogway.ui.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import oogway.logic.Oogway;
import oogway.logic.commands.Command;
import oogway.logic.commands.CommandResult;
import oogway.logic.commands.ExitCommand;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    private static final String MESSAGE_INTRO = "Greetings, young one. I am Master Oogway.\n"
            + "Enlighten me... What do you seek?";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Oogway oogway;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image oogwayImage = new Image(this.getClass().getResourceAsStream("/images/oogway.jpg"));

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Introduction message
        dialogContainer.getChildren().add(
                DialogBox.getOogwayDialog(MESSAGE_INTRO, oogwayImage)
        );
    }

    /** Injects the Oogway instance */
    public void setOogway(Oogway oogway) {
        this.oogway = oogway;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Oogway's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        Command command = oogway.parseCommand(input);
        CommandResult<?> result = oogway.executeCommand(command);

        // Check if the command is an exit command
        if (ExitCommand.isExit(command)) {
            exitMessage(result.getMessage());
            return;
        }

        String response = result.getMessage();
        DialogBox oogwayDialog = DialogBox.getOogwayDialog(response, oogwayImage);

        // Set the style of the dialog box based on the success of the command
        if (!result.isSuccess()) {
            oogwayDialog.setErrorStyle();
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                oogwayDialog
        );
        userInput.clear();
    }

    /**
     * Displays the exit message from Oogway.
     */
    private void exitMessage(String message) {
        dialogContainer.getChildren().add(
                DialogBox.getOogwayDialog(message, oogwayImage)
        );
        userInput.clear();

        // Create a pause transition
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> Platform.exit());
        pause.play();
    }
}
