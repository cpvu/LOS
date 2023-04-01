package src.los.controller;

import src.los.common.PlayerClass;
import src.los.controller.SceneController;

public class characterSelectionController {
    public static PlayerClass chosenCharacter;

    public void chooseNaruto() {
        SceneController.chosenCharacter = PlayerClass.NARUTO;
    }
    public void chooseSasuke() {
        SceneController.chosenCharacter = PlayerClass.SASUKE;
    }
}
