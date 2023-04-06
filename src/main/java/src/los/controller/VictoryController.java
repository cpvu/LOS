package src.los.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import src.los.game.SpaceDriver;
import java.net.URL;
import java.util.ResourceBundle;

public class VictoryController implements Initializable {
    @FXML
    public ImageView charPortrait;
    @FXML
    public Text textDialogue;

    public void setCharPortrait() {
        charPortrait.setImage(new Image(SpaceDriver.chosenCharacter.getDialogueImage()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCharPortrait();
    }
}


