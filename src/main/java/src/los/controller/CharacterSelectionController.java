package src.los.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import src.los.common.PlayerClass;

import java.io.IOException;

public class CharacterSelectionController {
    public static PlayerClass chosenCharacter;

    public void chooseNaruto() {
        chosenCharacter = PlayerClass.NARUTO;
    }
    public void chooseSasuke() {
        chosenCharacter = PlayerClass.SASUKE;
    }

    public void selectCharacter(ActionEvent event) throws IOException {
        String id = ((Node) event.getSource()).getId();

        if (id.equals("sasuke")) {
            chooseSasuke();
        } else {
            chooseNaruto();
        }

        //Initialize the player class
        SceneController.getInstance().createGameStage();
        SceneController.getInstance().showGameStage();

    }
}
