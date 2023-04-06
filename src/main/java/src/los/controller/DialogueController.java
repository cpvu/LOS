package src.los.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import src.los.game.SpaceDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogueController implements Initializable  {
    @FXML
    public ImageView charPortrait;
    @FXML
    public Text textDialogue;

    public DialogueController() throws IOException {
    }

    public void setCharPortrait() {
        charPortrait.setImage(new Image(SpaceDriver.chosenCharacter.getDialogueImage()));
    }
    @FXML
    private void confirmDialogue() throws IOException {
        SceneController.getInstance().showGameStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCharPortrait();
    }
}
