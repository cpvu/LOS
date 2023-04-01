package src.los;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import src.los.controller.SceneController;
import src.los.game.mainMenu;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneController.getInstance().showMainMenu();
    }
}
