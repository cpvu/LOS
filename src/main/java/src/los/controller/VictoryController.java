package src.los.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import src.los.game.SpaceDriver;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The VictoryController class is responsible for controlling the victory screen in the SpaceDriver game.
 * It implements the Initializable interface to initialize the screen with the appropriate character portrait and
 * dialogue text.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class VictoryController implements Initializable {
    @FXML
    public ImageView charPortrait;
    @FXML
    public Text textDialogue;

    /**
     * Sets the character portrait image by retrieving it from the chosenCharacter instance of the SpaceDriver class.
     */
    public void setCharPortrait() {
        charPortrait.setImage(new Image(SpaceDriver.chosenCharacter.getDialogueImage()));
    }

    /**
     * Initializes the victory screen with the appropriate character portrait and dialogue text.
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle localize the root object, or null if the root object was not localized.
\
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCharPortrait();
    }
}


