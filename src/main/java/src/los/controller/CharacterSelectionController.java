package src.los.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import src.los.common.PlayerClass;
import src.los.game.SpaceDriver;

import java.io.IOException;

public class CharacterSelectionController {

    public PlayerClass chosenCharacter;

    public void chooseNaruto() { SpaceDriver.chosenCharacter = PlayerClass.NARUTO; }
    public void chooseSasuke() {
        SpaceDriver.chosenCharacter = PlayerClass.SASUKE;
    }

    public void selectCharacter(ActionEvent event) throws IOException {
        String id = ((Node) event.getSource()).getId();

        if (id.equals("sasuke")) {
            chooseSasuke();
        } else {
            chooseNaruto();
        }
        //Initialize the player class
        initializeGameStage();
    }

    public void initializeGameStage () throws IOException {
        SceneController.getInstance().createGameStage();
        SceneController.getInstance().showGameStage();
        SceneController.getInstance().createDialogue();
    }
}
