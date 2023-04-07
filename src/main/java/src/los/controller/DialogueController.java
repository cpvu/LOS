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

/**
 * The DialogueController class controls the dialogue scene of the game and initializes its elements.
 *
 * Implements the Initializable interface from JavaFX to provide a standard way to initialize components
 * after the FXML file has been loaded.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class DialogueController implements Initializable  {
    @FXML
    public ImageView charPortrait;
    @FXML
    public Text textDialogue;

    /**
     * Sets the character portrait in the image view by loading the image from the chosen character's dialogue image path.
     */
    public void setCharPortrait() {
        charPortrait.setImage(new Image(SpaceDriver.chosenCharacter.getDialogueImage()));
    }

    /**
     * The event handler for the confirmation button in the dialogue scene.
     * Navigates the user to the game stage by calling the showGameStage() method from the SceneController class.
     * @throws IOException when there is an error while loading the game stage FXML file.
     */
    @FXML
    private void confirmDialogue() throws IOException {
        SceneController.getInstance().showGameStage();
    }

    /**
     * Initializes the dialogue scene by setting the character portrait in the image view.
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCharPortrait();
    }
}
