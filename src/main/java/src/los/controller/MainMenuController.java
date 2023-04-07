package src.los.controller;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The MainMenuController class represents the controller for the main menu of the game.
 *
 * It handles the user's interactions with the start button and quit button.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class MainMenuController {
    private Stage stage;

    public MainMenuController() throws IOException {
    }

    /**
     * Handles the user's interaction with the start button.
     * It shows the character select scene.
     * @param actionEvent the action event triggered by the start button.
     * @throws IOException if an I/O error occurs while loading the character select scene.
     */
    public void startButton(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance().showCharacterSelect();
    }

    /**
     * Handles the user's interaction with the quit button.
     * @param event the action event triggered by the quit button.
     */
    public void quitGame(ActionEvent event) {
            System.exit(0);
    }
}