package src.los.controller;

import javafx.fxml.FXML;
import src.los.common.MapStages;
import src.los.game.SpaceDriver;

import java.io.IOException;

public class DialogueController {
    @FXML
    private void confirmDialogue() throws IOException {
        SceneController.getInstance().showGameStage();
    }
}
