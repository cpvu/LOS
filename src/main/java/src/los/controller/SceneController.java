package src.los.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.los.common.MapStages;
import src.los.common.PlayerClass;
import src.los.game.SpaceDriver;
import java.io.IOException;

/**
 * The SceneController class manages the creation and display of the different scenes in the Legend of Shinobi game.
 *
 * It is a singleton class that allows only one instance to be created.
 * It creates the main menu, character selection, game stage, dialogue and victory scenes.
 * It also loads background images for each scene and generates dialogue text for the dialogue scene.
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

    private Scene dialogueScene;
    private Scene victoryScene;
    private static PlayerClass chosenCharacter;
    private SceneController() throws IOException {
        currentStage = new Stage();
        currentStage.setTitle("Legend of Shinobi");
        currentStage.setResizable(false);
        currentStage.setWidth(GAME_WIDTH);
        currentStage.setHeight(GAME_HEIGHT);

        characterSelection = createCharacterSelection();
        mainMenu = createMainMenu();
    }

    /**
     * Returns the singleton instance of SceneController.
     * If an instance does not exist, it creates a new one.
     * @return The singleton instance of SceneController
     * @throws IOException if an error occurs while creating a new instance of SceneController
     */
    public static SceneController getInstance() throws IOException {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    /**
     * Creates a new background image for a given scene and sets it as the background.
     * If no background image is found, it throws a RuntimeException.
     * @param sceneBackground The AnchorPane where the background image will be displayed
     * @param sceneType A string representing the type of scene (game, menu, LEVEL_ONE, LEVEL_TWO, or LEVEL_THREE)
     * @throws RuntimeException if no background image is found for the given sceneType
     */
    public void createBackground(AnchorPane sceneBackground, String sceneType) {
        String imageString;

        switch (sceneType) {
            case "game":
                imageString = "gameStageBgOne.png";
                break;
            case "menu":
                imageString = "mainMenuBg.png";
                break;
            case "LEVEL_ONE":
                imageString = "gameStageBgOne.png";
                break;
            case "LEVEL_TWO":
                imageString = "bgLevelTwo.png";
                break;
            case "LEVEL_THREE":
                imageString = "bgLevelThree.png";
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

    /**
     * use JavaFX's FXMLLoader to load FXML files for the character scenes and return a new Scene object.
     * @return a new Scene object for character
     * @throws IOException if source file not found
     */
    private Scene createCharacterSelection() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("characterSelection.fxml"));
        return new Scene(fxmlLoader.load());
    }

    /**
     * use JavaFX's FXMLLoader to load FXML files for the main menu scenes and return a new Scene object.
     * @return a new Scene object for main menu
     * @throws IOException if source file not found
     */
    private Scene createMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("mainMenu.fxml"));
        Scene menu = new Scene(fxmlLoader.load());

        AnchorPane sceneBackground = (AnchorPane) menu.lookup("#menu");
        createBackground(sceneBackground, "menu");

        return menu;
    }

    /**
     * Loads the game stage FXML file and creates a new game scene. The background of the scene is set using the current level.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public void createGameStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("gameStage.fxml"));
        Scene gameScene = new Scene(fxmlLoader.load());

        AnchorPane sceneBackground = (AnchorPane) gameScene.lookup("#background");
        createBackground(sceneBackground, String.valueOf(SpaceDriver.currentLevel));

        this.gameStage = gameScene;
    }

    /**
     * Creates a dialogue by loading an FXML file and setting it as the scene.
     * This method also sets the character portrait image based on the chosen character.
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    public void createDialogue() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("dialogue.fxml"));
        Scene dialogueScene = new Scene(fxmlLoader.load());

        ImageView characterPortrait = (ImageView) gameStage.getRoot().lookup("#characterPortrait");
        characterPortrait.setImage(new Image(SpaceDriver.chosenCharacter.getDialogueImage()));

        this.dialogueScene = dialogueScene;
    }

    /**
     * Returns a string representing a dialogue based on the current state of the game.
     * @return a string representing a dialogue based on the current state of the game
     */
   private String generateDialogue() {
        if (SpaceDriver.chosenCharacter == PlayerClass.NARUTO) {
            return "Dattebayo! That was tough, I need to find Sasuke!";
        } else if
        (SpaceDriver.currentLevel == MapStages.LEVEL_THREE) {
            return "So you are Pain.. you are JavaFx";
        }
        return "Javafx is easy.";
    }

    /**
     * Displays a dialogue scene with generated text and updates the level label.
     * @param currentLevel the current level represented by a MapStages object
     * @throws IOException if an input or output exception occurs
     */
    public void showDialogue(MapStages currentLevel) throws IOException {
        Text dialogueText = (Text) dialogueScene.getRoot().lookup("#textDialogue");
        dialogueText.setText(generateDialogue());

        Label text = (Label) gameStage.getRoot().lookup("#level");
        text.setText(String.valueOf(SpaceDriver.currentLevel.getLevelName()));

        currentStage.setScene(dialogueScene);
        currentStage.show();
    }

    /**
     * Loads the FXML file for the victory screen and creates a new Scene from it.
     * Sets the created Scene to the instance variable victoryScene.
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    public void createVictory() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpaceDriver.Player.class.getResource("victory.fxml"));
        Scene victoryScene = new Scene(fxmlLoader.load());
        this.victoryScene = victoryScene;
    }

    /**
     * Displays the victory scene and sets the dialogue text based on the chosen player character.
     * @throws IOException if there is an error creating the victory scene.
     */
    public void showVictory() throws IOException {
        createVictory();
        Text dialogueText = (Text) dialogueScene.getRoot().lookup("#textDialogue");

        if (SpaceDriver.chosenCharacter == PlayerClass.NARUTO) {
            dialogueText.setText("Dattebayo! I did it!");
        } else {
            dialogueText.setText("My revenge is complete!");
        }
        currentStage.setScene(victoryScene);
        currentStage.show();
    }

    /**
     * Loads the game background image for the current level and sets it as the background for the game scene.
     * @param currentLevel the current level of the game being played
     */
    public void loadGameBg(MapStages currentLevel) {
        AnchorPane sceneBackground = (AnchorPane) gameStage.lookup("#background");
        createBackground(sceneBackground, String.valueOf(currentLevel));
    }

    /**
     * Sets the current stage's scene to the main menu and displays it.
     */
    public void showMainMenu() {
        currentStage.setScene(mainMenu);
        currentStage.show();
    }

    /**
     * Sets the current stage's scene to the character selection screen and displays it.
     */
    public void showCharacterSelect() {
        currentStage.setScene(characterSelection);
        currentStage.show();
    }

    /**
     * Sets the current stage's scene to the game stage and displays it.
     */
    public void showGameStage() {
        currentStage.setScene(gameStage);
        currentStage.show();
    }

    /**
     * Returns the game stage scene.
     * @return the game stage scene
     */
    public Scene getGameStage() {
        return this.gameStage;
    }
}
