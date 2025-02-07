package oogway.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import oogway.logic.Oogway;
import oogway.ui.gui.MainWindow;

/**
 * A GUI for Oogway using FXML.
 */
public class Main extends Application {

    private final Oogway oogway = new Oogway(null);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setOogway(oogway);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
