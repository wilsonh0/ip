package oogway.ui.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 *
 * Adapted from https://github.com/se-edu/javafx-tutorial
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        dialog.setWrapText(true);
        displayPicture.setImage(img);

        roundImage();
    }

    private void roundImage() {
        Circle clip = new Circle();
        clip.setRadius(displayPicture.getFitWidth() / 2);
        clip.setCenterX(displayPicture.getFitWidth() / 2);
        clip.setCenterY(displayPicture.getFitHeight() / 2);

        displayPicture.setClip(clip);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Returns a DialogBox object representing the user's dialog.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img The image to be displayed in the dialog box.
     * @return A DialogBox object representing the user's dialog.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a DialogBox object representing Oogway's dialog.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img The image to be displayed in the dialog box.
     * @return A DialogBox object representing Oogway's dialog.
     */
    public static DialogBox getOogwayDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Sets the style of the dialog box to indicate an error.
     */
    public void setErrorStyle() {
        dialog.getStyleClass().add("error-label");
    }
}
