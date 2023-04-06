package src.los.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;

    public MainMenuController() throws IOException {
    }

    public void startButton(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance().showCharacterSelect();
    }

    public void quitGame(ActionEvent event) {
            System.exit(0);
    }
}