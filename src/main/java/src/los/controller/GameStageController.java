package src.los.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import src.los.common.PlayerClass;
import src.los.game.SpaceDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The GameStageController class is responsible for controlling the game stage view of the application.
 *
 * It implements the Initializable interface after its root element has been completely processed.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class GameStageController implements Initializable {
    @FXML
    public Label characterName;

    @FXML
    public StackPane gamePane;

    @FXML
    public Label scoreLabel;

    @FXML
    public Label level;
    @FXML
    public ImageView characterPortrait;

    /**
     * Set the game pane to the game.
     * @param gamePane the target game pane
     */
    private void setGamePane (Canvas gamePane) {
        this.gamePane.getChildren().add(gamePane);
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        characterName.setText(SpaceDriver.chosenCharacter.toString());
        level.setText(String.valueOf(SpaceDriver.currentLevel.getLevelName()));
        SpaceDriver gameDriver = new SpaceDriver();
        Canvas gameScene = gameDriver.initializeGameScene();
        setGamePane(gameScene);
    }
}
