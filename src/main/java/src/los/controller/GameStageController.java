package src.los.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import src.los.common.PlayerClass;
import src.los.game.SpaceDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameStageController implements Initializable {
    @FXML
    public Label characterName;
    @FXML
    public Label level;

    @FXML
    public StackPane gamePane;

    @FXML
    public Label scoreLabel;

    private void setGamePane (Canvas gamePane) {
        this.gamePane.getChildren().add(gamePane);
    }

    public void setLevel(String message){
        level.setText(message);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        characterName.setText(SpaceDriver.chosenCharacter.toString());
        level.setText(String.valueOf(SpaceDriver.currentLevel.getLevelName()));
        SpaceDriver gameDriver = new SpaceDriver();
        Canvas gameScene = gameDriver.initializeGameScene();
        setGamePane(gameScene);
    }
}
