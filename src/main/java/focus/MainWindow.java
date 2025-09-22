package focus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Focus focus;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image focusImage = new Image(this.getClass().getResourceAsStream("/images/focus.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Focus instance */
    public void setFocus(Focus f) {
        focus = f;
        // Show greeting on startup (same text as your CLI welcome)
        dialogContainer.getChildren().add(
                DialogBox.getFocusDialog(focus.getGreeting(), focusImage)
        );
    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = focus.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getFocusDialog(response, focusImage)
        );
        userInput.clear();

        if (focus.isExitRequested()) {
            javafx.application.Platform.exit(); // close after the response renders
        }

    }

}
