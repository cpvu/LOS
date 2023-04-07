package src.los;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import src.los.controller.SceneController;

import java.io.IOException;

/**
 * Drive the program
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class Main extends Application {
    /**
     * Start the game.
     * @param stage the primary stage for the application
     * @throws IOException if an error occurs while loading the main menu FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        SceneController.getInstance().showMainMenu();
    }
}
