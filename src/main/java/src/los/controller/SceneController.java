package src.los.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import src.los.common.MapStages;
import src.los.common.PlayerClass;
import src.los.game.CharacterSelect;
import src.los.game.mainMenu;

import java.io.IOException;
import java.io.InputStream;

public class SceneController {
    private static SceneController instance = null;
    private Stage currentStage;
    private final Scene mainMenu;
    private Scene characterSelection;

    public SceneController() throws IOException {
        currentStage = new Stage();
        characterSelection = createCharacterSelection();
        mainMenu = createMainMenu();
    }

    public static SceneController getInstance() throws IOException {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    private Scene createCharacterSelection() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CharacterSelect.class.getResource("characterSelection.fxml"));
        return new Scene(fxmlLoader.load());
    }

    private Scene createMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainMenu.class.getResource("mainMenu.fxml"));
        return new Scene(fxmlLoader.load());
    }

    public void showMainMenu() {
        currentStage.setScene(mainMenu);
        currentStage.show();
    }

    public void showCharacterSelect() {
        currentStage.setScene(characterSelection);
        currentStage.show();
    }
    public void setScene(ActionEvent event) {

    }
}
