package src.los.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import src.los.common.PlayerClass;

import java.net.URL;
import java.util.ResourceBundle;

public class GameStageController implements Initializable {
    @FXML
    public Label characterName;

    public void setCharacterNameLabel(String name) {
        System.out.println(characterName);
        characterName.setText(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        characterName.setText(CharacterSelectionController.chosenCharacter.toString());
    }
}
