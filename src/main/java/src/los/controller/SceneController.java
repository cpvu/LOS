package src.los.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import src.los.common.PlayerClass;
import src.los.game.Player;

import java.io.IOException;

/**
 * The SceneController class manages the different scenes and stages of the game. It controls the creation of the
 * main menu and character selection screens, as well as the game stage. It also provides methods to switch between
 * different scenes and to create and set the backgrounds for each scene.
 * @author Calvin Vu & Hanxiao Mao
 * @version 1.0
 */
public class SceneController {
    private static final int GAME_WIDTH = 700;
    private static final int GAME_HEIGHT = 500;
    private static SceneController instance = null;
    private final Stage currentStage;
    private final Scene mainMenu;
    private final Scene characterSelection;
    public Scene gameStage;
    public static PlayerClass chosenCharacter;

    public SceneController() throws IOException {
        currentStage = new Stage();
        currentStage.setTitle("Legend of Shinobi");
        currentStage.setResizable(false);
        currentStage.setWidth(GAME_WIDTH);
        currentStage.setHeight(GAME_HEIGHT);

        characterSelection = createCharacterSelection();
        mainMenu = createMainMenu();
    }

    /**
     * Returns the instance of the SceneController class, creating a new instance if one does not exist.
     *
     * @return The instance of the SceneController class.
     * @throws IOException If there is an error creating the instance.
     */
    public static SceneController getInstance() throws IOException {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    /**
     * Sets the background of the scene using the specified scene type.
     *
     * @param sceneBackground The AnchorPane that the background should be set on.
     * @param sceneType The type of scene that the background should be set for.
     * @throws RuntimeException If there is no background image found for the specified scene type.
     */
    public void createBackground(AnchorPane sceneBackground, String sceneType) {
        String imageString;

        switch (sceneType) {
            case "game":
                imageString = "bg1.png";
                break;
            case "menu":
                imageString = "gamebg.png";
                break;
            default:
                throw new RuntimeException("No background image found");
        }

        Image image = new Image(imageString);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(GAME_WIDTH, GAME_HEIGHT, true, true, true, true));

        sceneBackground.setBackground(new Background(backgroundImage));
    }

    //Creates the character selection scene by loading the FXML file.
    private Scene createCharacterSelection() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Player.class.getResource("characterSelection.fxml"));
        return new Scene(fxmlLoader.load());
    }

    private Scene createMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Player.class.getResource("mainMenu.fxml"));
        Scene menu = new Scene(fxmlLoader.load());

        AnchorPane sceneBackground = (AnchorPane) menu.lookup("#menu");

        createBackground(sceneBackground, "menu");

        return menu;
    }

    /**
     * Creates the game stage scene by loading the FXML file and setting the background.
     * @throws IOException if an error occurs while loading the FXML file.
     */
    public void createGameStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Player.class.getResource("gameStage.fxml"));
        Scene gameScene = new Scene(fxmlLoader.load());
        AnchorPane sceneBackground = (AnchorPane) gameScene.lookup("#background");

        createBackground(sceneBackground, "game");
        this.gameStage = gameScene;
    }

    public void loadNewLevel() {

    }

    /**
     *Shows the main menu scene on the current stage.
     */
    public void showMainMenu() {
        currentStage.setScene(mainMenu);
        currentStage.show();
    }

    /**
     * Shows the character selection scene on the current stage.
     */
    public void showCharacterSelect() {
        currentStage.setScene(characterSelection);
        currentStage.show();
    }

    /**
     * Shows the game stage scene on the current stage.
     */
    public void showGameStage() {
        currentStage.setScene(gameStage);
        currentStage.show();
    }
}
